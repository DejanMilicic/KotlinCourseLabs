package lab4.task1

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

open class BankAccount(open val accountNumber: String, open val accountHolderName: String, var accountBalance: Double) {
    constructor(accountNumber: String, accountHolderName: String) : this(accountNumber, accountHolderName, 0.0)

    open fun deposit(amount: Double) {
        accountBalance += amount
    }

    open fun withdraw(amount: Double): Boolean {
        if (accountBalance >= amount) {
            accountBalance -= amount
            return true
        } else return false
    }

    open fun getBalance(): Double {
        return accountBalance
    }

    open fun displayAccountInfo(): Unit {
        print(
            "Account Holder: [" + this.accountHolderName + "]\n" +
                    "Account Number: [" + this.accountNumber + "]\n" +
                    "Balance: [" + this.accountBalance + "]\n"
        )
    }
}


fun main() {

    // Create a Bank Account
    val account = BankAccount("123456789", "John Doe")

    // Display account information
    account.displayAccountInfo()

    // Deposit some money
    account.deposit(1000.0)

    // Withdraw some money
    account.withdraw(500.0)

    // Display updated account information
    account.displayAccountInfo()
}
