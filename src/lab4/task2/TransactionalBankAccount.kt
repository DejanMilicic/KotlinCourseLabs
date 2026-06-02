package lab4.task2

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import lab4.task1.BankAccount

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

enum class TransactionType {
    DEPOSIT, WITHDRAW
}

enum class TransactionStatus {
    SUCCESS, FAILURE
}

class Transaction(
    val transactionDate: LocalDateTime,
    val transactionType: TransactionType,
    val amount: Double,
    val oldBalance: Double,
    val newBalance: Double,
    val transactionStatus: TransactionStatus
) {}

class TransactionalBankAccount(accountNumber: String, accountHolderName: String) :
    BankAccount(accountNumber, accountHolderName) {
    val transactions = ArrayList<Transaction>()

    override fun deposit(amount: Double): Unit {
        super.deposit(amount)
        transactions.add(
            Transaction(
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                amount,
                super.getBalance() - amount,
                super.getBalance(),
                TransactionStatus.SUCCESS
            )
        )
    }

    override fun withdraw(amount: Double): Boolean {
        if (super.getBalance() < amount) {
            transactions.add(
                Transaction(
                    LocalDateTime.now(),
                    TransactionType.WITHDRAW, amount,
                    super.getBalance(),
                    super.getBalance() - amount,
                    TransactionStatus.FAILURE
                )
            )
            return false
        } else {
            super.withdraw(amount)
            transactions.add(
                Transaction(
                    LocalDateTime.now(),
                    TransactionType.WITHDRAW, amount,
                    super.getBalance() + amount,
                    super.getBalance(),
                    TransactionStatus.SUCCESS
                )
            )
            return true
        }
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions.sortedByDescending { it.transactionDate }
    }

    fun getAllTransactionsBy(predicate: (Transaction) -> Boolean): List<Transaction> {
        return transactions.filter(predicate).sortedByDescending { it.transactionDate }
    }

    fun getTransactionsBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        return transactions.filter { it.transactionDate >= startDate && it.transactionDate <= endDate }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllFailedTransactions(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.FAILURE }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllSuccessfulTransactions(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.SUCCESS }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllFailedDeposits(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.FAILURE && it.transactionType == TransactionType.WITHDRAW }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllFailedWithdrawals(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.FAILURE && it.transactionType == TransactionType.DEPOSIT }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllSuccessfulDeposits(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.SUCCESS && it.transactionType == TransactionType.DEPOSIT }
            .sortedByDescending { it.transactionDate }
    }

    fun getAllSuccessfulWithdrawals(): List<Transaction> {
        return transactions.filter { it.transactionStatus == TransactionStatus.SUCCESS && it.transactionType == TransactionType.WITHDRAW }
            .sortedByDescending { it.transactionDate }
    }

    override fun displayAccountInfo(): Unit {
        println(
            "Account Holder: [$accountHolderName]\n" +
                    "Account Number: [$accountNumber]\n" +
                    "Balance: [${super.getBalance()}]\n"
        )
        if (transactions.isEmpty()) {
            println("No transactions recorded.\n")
        } else {
            for (transaction in transactions) {
                println(
                    "Transaction Date: [${transaction.transactionDate.prettyPrint()}]\n" +
                            "Transaction Type: [${transaction.transactionType}]\n" +
                            "Amount: [${transaction.amount}]\n" +
                            "Old Balance: [${transaction.oldBalance}]\n" +
                            "New Balance: [${transaction.newBalance}]\n" +
                            "Status: [${transaction.transactionStatus}]\n"
                )
            }

        }
    }

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
