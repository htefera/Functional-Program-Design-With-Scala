import frp._

class BankAccount:
  private var balance = 0

  def deposit(amount: Int): Unit =
    if amount > 0 then balance = balance + amount
  def withdraw(amount: Int): Int =
    if 0 < amount && amount <= balance then
      balance = balance - amount
      balance
    else throw Error("insufficient funds")

//body of the main clause


val account = BankAccount()
account.deposit(50)
account.withdraw(20)
account.withdraw(20)
account.withdraw(15)






