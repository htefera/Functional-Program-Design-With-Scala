trait Ring[A] {
  def plus(x: A, y: A): A
  def mult(x: A, y: A): A
  def inverse(x: A): A
  def zero: A
  def one: A
}

object Ring {
  implicit val ringInt: Ring[Int] = new Ring[Int] {
    def plus(x: Int, y: Int): Int = x + y
    def mult(x: Int, y: Int): Int = x * y
    def inverse(x: Int): Int = -x
    def zero: Int = 0
    def one: Int = 1
  }
}

def plusAssociativity[A](x: A, y: A, z: A)(implicit ring: Ring[A]): Boolean =
  ring.plus(ring.plus(x, y), z) == ring.plus(x, ring.plus(y, z))

//(x*y)*z= x*(y*z)

def multAssociativity[A](x: A, y: A, z: A)(implicit ring: Ring[A]): Boolean =
  ring.mult(ring.mult(x, y), z) == ring.mult(x, ring.mult(y, z))

def numberplusAssociativity[Int](x: Int, y: Int, z: Int)(implicit ring: Ring[Int]): Boolean =
  ring.plus(ring.plus(x, y), z) == ring.plus(x, ring.plus(y, z))





