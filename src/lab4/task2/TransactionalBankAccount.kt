package lab4.task2

import lab4.task1.BankAccount
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Transactional Bank Account Assignment
 *
 * Objective:
 * Implement a Transactional Bank Account system using Kotlin and Object-Oriented Programming principles.
 * Transactional Bank Account should extend [lab4.task1.BankAccount] from lab4.task1 and add ability to record
 * all transactions made with the account.
 *
 * 1. Create [TransactionalBankAccount] class which **extends** [lab4.task1.BankAccount] class,
 * and adds ability to record transaction on every deposit or withdrawal.
 *
 * 2. Transaction has the following attributes:
 *      - [transactionDate]: a date [LocalDateTime] when the transaction was made.
 *      - [transactionType]: a type of transaction can be [DEPOSIT] or [WITHDRAWAL]. Modeled as an enum.
 *      - [amount]: the amount that was deposited / withdrawn from the balance of the account.
 *      - [oldBalance]: the balance of the account before the transaction.
 *      - [newBalance]: the balance of the account after the transaction.
 *      - [transactionStatus] - status of transaction. [SUCCESS] if it was successful, [FAILURE] otherwise. Modeled as an enum.
 *
 * 3. Implement the following methods in [TransactionalBankAccount] class:
 *      - [deposit]: adds the specified amount to the account balance
 *        and records [DEPOSIT] transaction with a current time, amount, and status.
 *      - [withdraw]: deducts the specified amount from the account balance if sufficient funds are available,
 *        returns [true] of transaction was successful [false] otherwise.
 *        In addition to this, it records [WITHDRAWAL] transaction with a current time,
 *        amount and [SUCCESS] status if the transaction was successful. Otherwise, transaction should have [FAILURE] status.
 *      - [getBalance]: returns the current balance of the account. - we already have this method in the BankAccount class, overriding it would be redundant
 *      unless TransitionalBankAccount class had its own separate balance field
 *      - [getAllTransactions]: returns the list of all transactions sorted descending by transaction time.
 *      - [getAllTransactionsBy(predicate: (Transaction) -> Boolean)]: returns the list of all transactions that satisfy the provided predicate,
 *        sorted descending by transaction time.
 *      - [getTransactionsBetween(startDate: LocalDateTime, endDate: LocalDateTime)]: returns the list of transactions
 *        that occurred between the specified start and end dates, sorted descending by transaction time.
 *      - [getAllFailedTransactions]: returns the list of all transactions with a [FAILURE] status, sorted descending by transaction time.
 *      - [getAllSuccessfulTransactions]: returns the list of all transactions with a [SUCCESS] status, sorted descending by transaction time.
 *      - [getAllFailedDeposits]: returns the list of all failed deposit transactions, sorted descending by transaction time.
 *      - [getAllFailedWithdrawals]: returns the list of all failed withdrawal transactions, sorted descending by transaction time.
 *      - [getAllSuccessfulDeposits]: returns the list of all successful deposit transactions, sorted descending by transaction time.
 *      - [getAllSuccessfulWithdrawals]: returns the list of all successful withdrawal transactions, sorted descending by transaction time.
 *
 * 4. [TransactionalBankAccount] class should override the [displayAccountInfo] method to include transaction details.
 *      - Information should be printed in the following format:
 *      ```
 *      Account Holder: [ACCOUNT_HOLDER_NAME]
 *      Account Number: [ACCOUNT_NUMBER]
 *      Balance: [ACCOUNT_BALANCE]
 *
 *      Transactions:
 *
 *      Transaction Date: [PRETTY_FORMATED_TRANSACTION_DATE]
 *      Transaction Type: [TRANSACTION_TYPE]
 *      Amount: [TRANSACTION_AMOUNT]
 *      Old Balance: [OLD_BALANCE]
 *      New Balance: [NEW_BALANCE]
 *      Status: [TRANSACTION_STATUS]
 *
 *      Transaction Date: [PRETTY_FORMATED_TRANSACTION_DATE]
 *      Transaction Type: [TRANSACTION_TYPE]
 *      Amount: [TRANSACTION_AMOUNT]
 *      Old Balance: [OLD_BALANCE]
 *      New Balance: [NEW_BALANCE]
 *      Status: [TRANSACTION_STATUS]
 *      ```
 *     If there is no transaction information should be printed as following:
 *     ```
 *      Account Holder: [ACCOUNT_HOLDER_NAME]
 *      Account Number: [ACCOUNT_NUMBER]
 *      Balance: [ACCOUNT_BALANCE]
 *
 *      Transactions:
 *
 *      No transactions recorded.
 *      ```
 */

enum class TransactionType {
    DEPOSIT, WITHDRAWAL
}

enum class TransactionStatus {
    SUCCESS, FAILURE
}

data class Transaction(
    val transactionDate: LocalDateTime,
    val transactionType: TransactionType,
    val amount: Double,
    val oldBalance: Double,
    val newBalance: Double,
    val transactionStatus: TransactionStatus
)

class TransactionalBankAccount(
    accountNumber: String,
    accountHolderName: String,
    balance: Double
) : BankAccount(accountNumber, accountHolderName, balance) {
    constructor(accountNumber: String, accountHolderName: String) : this(accountNumber, accountHolderName, 0.0)

    private val transactions = mutableListOf<Transaction>()

    override fun deposit(amount: Double) {
        val oldBalance = getBalance()
        super.deposit(amount)
        transactions.add(
            Transaction(
                transactionDate = currentTime,
                TransactionType.DEPOSIT,
                amount,
                oldBalance,
                newBalance = getBalance(),
                TransactionStatus.SUCCESS
            )
        )
    }

    override fun withdraw(amount: Double): Boolean {
        val oldBalance = getBalance()
        val success = super.withdraw(amount)
        val status = if (success) TransactionStatus.SUCCESS else TransactionStatus.FAILURE
        transactions.add(
            Transaction(
                transactionDate = currentTime,
                TransactionType.WITHDRAWAL,
                amount,
                oldBalance,
                newBalance = getBalance(),
                status
            )
        )
        return success
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions.sortedByDescending { transaction -> transaction.transactionDate }
    }

    fun getAllTransactionsBy(predicate: (Transaction) -> Boolean): List<Transaction> {
        return transactions.filter(predicate).sortedByDescending { transaction -> transaction.transactionDate }
    }

    fun getTransactionsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionDate in startDate..endDate }
    }

    fun getAllFailedTransactions(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.FAILURE }
    }

    fun getAllSuccessfulTransactions(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.SUCCESS }
    }

    fun getAllFailedDeposits(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.FAILURE && transaction.transactionType == TransactionType.DEPOSIT }
    }

    fun getAllFailedWithdrawals(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.FAILURE && transaction.transactionType == TransactionType.WITHDRAWAL }
    }

    fun getAllSuccessfulDeposits(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.SUCCESS && transaction.transactionType == TransactionType.DEPOSIT }
    }

    fun getAllSuccessfulWithdrawals(): List<Transaction> {
        return getAllTransactionsBy { transaction -> transaction.transactionStatus == TransactionStatus.SUCCESS && transaction.transactionType == TransactionType.WITHDRAWAL }
    }

    override fun displayAccountInfo() {
        super.displayAccountInfo()
        println("\nTransactions:\n")
        if (transactions.isEmpty()) {
            println("No transactions recorded.")
        } else {
            for (transaction in getAllTransactions()) {
                println("Transaction Date: ${transaction.transactionDate.prettyPrint()}")
                println("Transaction Type: ${transaction.transactionType}")
                println("Amount: ${transaction.amount}")
                println("Old Balance: ${transaction.oldBalance}")
                println("New Balance: ${transaction.newBalance}")
                println("Status: ${transaction.transactionStatus}")
                println()
            }
        }
    }
}

private val currentTime: LocalDateTime get() = LocalDateTime.now()

private fun LocalDateTime.prettyPrint(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return this.format(formatter)
}

fun main() {
    println(currentTime.prettyPrint())
    // Create a Transactional Bank Account
    val account = TransactionalBankAccount("123456789", "John Doe")

    // Display account information
    account.displayAccountInfo()

    // Deposit some money
    account.deposit(1000.0)

    // Withdraw some money
    account.withdraw(500.0)

    // Display updated account information
    account.displayAccountInfo()
}
