package lab4.task2

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import lab4.task1.BankAccount
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
 *      - [getBalance]: returns the current balance of the account.
 *      - [getAllTranactions]: returns the list of all transactions sorted descending by transaction time.
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

private val currentTime: LocalDateTime get() = LocalDateTime.now()

private fun LocalDateTime.prettyPrint(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return this.format(formatter)
}

enum class TransactionType { DEPOSIT, WITHDRAWAL }
enum class TransactionStatus { SUCCESS, FAILURE }

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
    balance: Double = 0.0
) : BankAccount(accountNumber, accountHolderName, balance) {

    private val transactions = mutableListOf<Transaction>()

    override fun deposit(amount: Double) {
        val oldBalance = getBalance()
        super.deposit(amount)
        transactions.add(Transaction(currentTime, TransactionType.DEPOSIT, amount, oldBalance, getBalance(), TransactionStatus.SUCCESS))
    }

    override fun withdraw(amount: Double): Boolean {
        val oldBalance = getBalance()
        val success = super.withdraw(amount)
        transactions.add(Transaction(
            currentTime, TransactionType.WITHDRAWAL, amount, oldBalance,
            getBalance(),
            if (success) TransactionStatus.SUCCESS else TransactionStatus.FAILURE
        ))
        return success
    }

    fun getAllTranactions(): List<Transaction> =
        transactions.sortedByDescending { it.transactionDate }

    fun getAllTransactionsBy(predicate: (Transaction) -> Boolean): List<Transaction> =
        transactions.filter(predicate).sortedByDescending { it.transactionDate }

    fun getTransactionsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> =
        getAllTransactionsBy { it.transactionDate in startDate..endDate }

    fun getAllFailedTransactions(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.FAILURE }

    fun getAllSuccessfulTransactions(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.SUCCESS }

    fun getAllFailedDeposits(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.FAILURE && it.transactionType == TransactionType.DEPOSIT }

    fun getAllFailedWithdrawals(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.FAILURE && it.transactionType == TransactionType.WITHDRAWAL }

    fun getAllSuccessfulDeposits(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.SUCCESS && it.transactionType == TransactionType.DEPOSIT }

    fun getAllSuccessfulWithdrawals(): List<Transaction> =
        getAllTransactionsBy { it.transactionStatus == TransactionStatus.SUCCESS && it.transactionType == TransactionType.WITHDRAWAL }

    override fun displayAccountInfo() {
        super.displayAccountInfo()
        println("\nTransactions:\n")
        if (transactions.isEmpty()) {
            println("No transactions recorded.")
            return
        }
        getAllTranactions().forEach { t ->
            println("Transaction Date: ${t.transactionDate.prettyPrint()}")
            println("Transaction Type: ${t.transactionType}")
            println("Amount: ${t.amount}")
            println("Old Balance: ${t.oldBalance}")
            println("New Balance: ${t.newBalance}")
            println("Status: ${t.transactionStatus}\n")
        }
    }
}

fun main() {
    println(currentTime.prettyPrint())
    val account = TransactionalBankAccount("123456789", "John Doe")
    account.displayAccountInfo()
    account.deposit(1000.0)
    account.withdraw(500.0)
    account.displayAccountInfo()
}
