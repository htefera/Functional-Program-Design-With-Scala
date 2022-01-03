
def from(n: Int): LazyList[Int] = n #:: from(n+1)

val nats = from(0)
//multiple of natural numbers
val malti=nats.map(_ * 4)

from(10).take(10).toList
nats.take(20).toList
malti.take(15).toList

//nats.toList this is equvalent to printing all natural numbers. Perhaps the list would not finish

def sieve(s: LazyList[Int]): LazyList[Int] =
  s.head #:: sieve(s.tail.filter(_ % s.head != 0)) ///remove all the elements which are divisible by n

val primes= sieve(from(2))

primes.take(100).toList



def sqrtSeq(x: Double): LazyList[Double] =
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val guesses: LazyList[Double] = 1 #:: guesses.map(improve)
  guesses
def isGoodEnough(guess: Double, x: Double) =
  ((guess * guess - x) / x).abs < 0.0001

sqrtSeq(2).filter(isGoodEnough(_, 2)).head


sqrtSeq(4).filter(isGoodEnough(_, 4)).head

sqrtSeq(9).filter(isGoodEnough(_, 9)).head

val N= from(0)



