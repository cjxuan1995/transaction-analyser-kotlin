package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.UserInput
import java.math.BigDecimal

class TransactionProcessor {
    fun filterTransactionsByUserInput(transactions: List<Transaction>, input: UserInput): List<Transaction>{
        return transactions
            .filter { isTransactionFromAccount(it, input) || isTransactionToAccount(it, input) }
            .filter { isTransactionAfterOrAtFromTime(it, input) && isTransactionBeforeOrAtToTime(it, input) }
            .filter { isTransactionReversed(it, transactions) }
    }

    fun calculateRelativeBalance(filteredTransactions: List<Transaction>, input: UserInput): BigDecimal{
        return filteredTransactions
            // Negate the amount when the payment is made from this account
            .map { if (input.accountId == it.fromAccountId) it.amount.negate() else it.amount }
            .reduce { sum, element -> sum + element }
    }

    private fun isTransactionFromAccount(transaction: Transaction, input: UserInput): Boolean{
        return transaction.fromAccountId == input.accountId
    }

    private fun isTransactionToAccount(transaction: Transaction, input: UserInput): Boolean{
        return transaction.toAccountId == input.accountId
    }

    private fun isTransactionAfterOrAtFromTime(transaction: Transaction, input: UserInput): Boolean{
        return transaction.createdAt >= input.from
    }

    private fun isTransactionBeforeOrAtToTime(transaction: Transaction, input: UserInput): Boolean{
        return transaction.createdAt <= input.to
    }

    private fun isTransactionReversed(transaction: Transaction, transactions: List<Transaction>): Boolean{
        return transactions.none{ it.relatedTransaction == transaction.transactionId }
    }
}