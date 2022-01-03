
import scala.frp


class BankAccount {
  val balance = Var(0)

  def deposit(x: Int): Unit = {
    val curBalance = balance()
    balance() = curBalance + x
  }
  def withdraw(x: Int): Unit = {
    val curBalance = balance()
    balance() = curBalance - x
  }
}

def consolidated(accts: List[BankAccount]) =
  Signal(accts.map(_.balance()).sum)

val a = new BankAccount()
val b = new BankAccount()
val total = consolidated(List(a,b))


