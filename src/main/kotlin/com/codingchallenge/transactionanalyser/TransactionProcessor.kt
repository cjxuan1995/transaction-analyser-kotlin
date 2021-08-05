package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.UserInput
import java.math.BigDecimal

class TransactionProcessor {
    fun filterTransactionsByUserInput(transactions: List<Transaction>, input: UserInput): List<Transaction>{
        return transactions.filter { it.fromAccountId == input.accountId || it.toAccountId == input.accountId }
            .filter { it.createdAt > input.from && it.createdAt < input.to }
            .filter { ft -> transactions.none { t -> ft.transactionId == t.relatedTransaction } }
    }

    fun calculateRelativeBalance(filteredTransactions: List<Transaction>, input: UserInput): BigDecimal{
        return filteredTransactions.map { if (input.accountId == it.fromAccountId) it.amount.negate() else it.amount }
            .reduce { sum, element -> sum + element }
    }
}