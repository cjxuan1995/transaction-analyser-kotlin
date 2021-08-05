package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.UserInput
import java.math.BigDecimal

class TransactionProcessor {
    fun filterTransactionsByUserInput(transactions: List<Transaction>, input: UserInput): List<Transaction>{
        return transactions
            // find the transactions related to the account
            .filter { it.fromAccountId == input.accountId || it.toAccountId == input.accountId }
            // find the transactions where the createdAt is after the "from" time and before the "to" time
            .filter { it.createdAt > input.from && it.createdAt < input.to }
            // Remove transactions that get reversed
            .filter { ft -> transactions.none { t -> ft.transactionId == t.relatedTransaction } }
    }

    fun calculateRelativeBalance(filteredTransactions: List<Transaction>, input: UserInput): BigDecimal{
        return filteredTransactions
            // Negate the amount when the payment is made from this account
            .map { if (input.accountId == it.fromAccountId) it.amount.negate() else it.amount }
            .reduce { sum, element -> sum + element }
    }
}