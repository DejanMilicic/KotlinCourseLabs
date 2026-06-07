package lab4.task1

open class BankAccount(
    val accountNumber: String,
    val accountHolderName: String,
    private var balance: Double = 0.0
) {
    constructor(accountNumber: String, accountHolderName: String) : this(accountNumber, accountHolderName, 0.0)

    open fun deposit(amount: Double) {
        require(amount > 0) { "Deposit amount must be positive, but was $amount" }
        balance += amount
    }

    open fun withdraw(amount: Double): Boolean {
        require(amount > 0) { "Withdrawal amount must be positive, but was $amount" }
        if (amount > balance) return false
        balance -= amount
        return true
    }

    fun getBalance(): Double = balance

    open fun displayAccountInfo() {
        println("Account Holder: $accountHolderName")
        println("Account Number: $accountNumber")
        println("Balance: $balance")
    }
}

fun main() {
    val account = BankAccount("123456789", "John Doe")
    account.displayAccountInfo()
    account.deposit(1000.0)
    account.withdraw(500.0)
    account.displayAccountInfo()
}