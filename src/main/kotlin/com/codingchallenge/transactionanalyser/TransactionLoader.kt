package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.TransactionBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.stream.Collectors

class TransactionLoader(private val input: InputStream) {
    fun loadTransactionsFromCsv(): List<Transaction>{
        val transactions: List<Transaction>
        BufferedReader(InputStreamReader(input)).use {
            transactions = it.lines()
                .skip(1)
                .map(this::buildTransactionFromCsvLine)
                .collect(Collectors.toList())
        }
        return transactions
    }

    private fun buildTransactionFromCsvLine(line: String): Transaction{
        val values = line.split(",").map { it.trim() }
        var transactionBuilder = TransactionBuilder(values[0])
            .withFromAccountId(values[1])
            .withToAccountId(values[2])
            .withCreatedAt(values[3])
            .withAmount(values[4])
            .withType(values[5])
        if (values.size == 7){
            transactionBuilder = transactionBuilder.withRelatedTransaction(values[6])
        }
        return transactionBuilder.build()
    }
}