package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll


abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  //H is type of the element where A is type of the heap
  lazy val genHeap: Gen[H] = for {
      k <- arbitrary[Int]
      m <- oneOf(const(empty), genHeap)
    } yield insert(k, m)

 // given Arbitrary[H] = Arbitrary(genHeap)
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)


  property("Adding the minimal element, and then finding it, should return the element itself") =
    forAll { (h: H) =>
      val m = if isEmpty(h) then 0 else findMin(h)
      findMin(insert(m, h)) == m
             }



  property("if you insert an element into an empty heap, then find the minimum of the resulting heap, you get the element back") =
    forAll { (a: Int) =>
      val h = insert(a, empty)
      findMin(h) == a
    }

  property("If you insert any 2 elements into an empty heap,  finding the minimum must return the smallest of the 2 elements") =
    forAll { (a: Int, b: Int) =>
      val n = insert(b, insert(a, empty))
      findMin(n) == Math.min(a, b)
  }

  property("insert an element into an empty heap and delete one element from it should produce an empty heap") =
    forAll { (a: Int) =>
      deleteMin(insert(a, empty)) == empty
      }
  property("continually finding and deleting the minimum should produce a sorted sequence") =
    forAll {
      (h: H) =>
      def isSorted(h: H): Boolean = {
        if (isEmpty(h)) true
        else {
          val min = findMin(h)
          val newHeap = deleteMin(h)
          isEmpty(newHeap) || min <= findMin(newHeap) && isSorted(newHeap)
        }
      }
      isSorted(h)
  }

  property ("Finding a minimum of the melding of any two heaps should return a minimum of one or the other")=
  {
    forAll { (h1: H, h2: H) =>
      findMin(meld(h1, h2)) == Math.min(findMin(h1), findMin(h2))
    }
  }

  property("2 heaps should be equal if continually removing minimum elements results in equal heaps") =
    forAll {
      (h1: H, h2: H) =>
        def heapEqual(h1: H, h2: H): Boolean =
          if (isEmpty(h1) && isEmpty(h2)) true
          else {
            val m1 = findMin(h1)
            val m2 = findMin(h2)
            m1 == m2 && heapEqual(deleteMin(h1), deleteMin(h2))
          }
        heapEqual(meld(h1, h2), meld(deleteMin(h1), insert(findMin(h1), h2)))
    }





