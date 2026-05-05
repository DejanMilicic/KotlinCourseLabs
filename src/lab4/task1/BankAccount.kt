package lab4.task1

open class BankAccount(
    private var accountNumber: String,
    private var accountHolderName: String,
    private var balance: Double
) {
    constructor(accountNumber: String, accountHolderName: String) : this(accountNumber, accountHolderName, 0.0)

    fun deposit(amount: Double): Double {
        if (amount <= 0) throw IllegalArgumentException("Amount must be higher than zero")
        balance += amount
        return balance
    }

    fun withdraw(amount: Double): Boolean {
        if (amount <= 0) throw IllegalArgumentException("Amount must be higher than zero")
        if (amount > balance) return false
        else {
            balance -= amount
            return true
        }
    }

    fun getBalance(): Double {
        return balance
    }

    fun displayAccountInfo() {
        println("Account Holder: $accountHolderName \nAccount Number: $accountNumber \nBalance: $balance \n")
    }

}

/**
 * Bank Account Assignment
 *
 * Objective:
 * Implement a simple Bank Account system using Kotlin and Object-Oriented Programming principles.
 *
 * Requirements:
 * 1. Create a [BankAccount] class with the following attributes:
 *    - [accountNumber]: a unique identifier for each bank account.
 *    - [accountHolderName]: name of the account holder.
 *    - [balance]: current balance in the account.
 *
 * 2. Implement the following methods in the [BankAccount] class:
 *    - [deposit]: adds the specified amount to the account balance.
 *    - [withdraw]: deducts the specified amount from the account balance if sufficient funds are available,
 *      returns `true` if the transaction was successful, `false` otherwise.
 *    - [getBalance]: returns the current balance of the account.
 *    - [displayAccountInfo]: prints out the account information including account number, account holder name, and current balance.
 *      - Information should be printed in the following format:
 *      ```
 *      Account Holder: [ACCOUNT_HOLDER_NAME]
 *      Account Number: [ACCOUNT_NUMBER]
 *      Balance: [ACCOUNT_BALANCE]
 *      ```
 * 3. Ensure that withdrawal is only allowed if the withdrawal amount is less than or equal to the current balance.
 *
 * 4. Create two class constructors:
 *    - One constructor accepts the account holder name, account holder number, and initial balance.
 *    - The second constructor accepts only the account holder name and account holder number.
 *      The initial balance for this constructor should always be set to 0.
 */


fun main() {
    val account = BankAccount("123456789", "John Doe")

    account.displayAccountInfo()
    account.deposit(1000.0)
    account.withdraw(500.0)
    account.displayAccountInfo()
}
