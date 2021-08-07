package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.TransactionBuilder
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TransactionLoader(private val input: InputStream) {
    fun loadTransactionsFromCsv(): List<Transaction>{
        val transactions: List<Transaction>
        BufferedReader(InputStreamReader(input)).use {
            val csvParser = CSVParser(it,
                CSVFormat.DEFAULT.builder()
                    .setHeader(Headers::class.java)
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .setNullString("")
                    .build())
            transactions = csvParser.map { csvRecord -> buildTransactionFromCsvLine(csvRecord) }
        }
        return transactions
    }

    private fun buildTransactionFromCsvLine(record: CSVRecord): Transaction{
        val transactionBuilder = TransactionBuilder(record.get(Headers.transactionId))
            .withFromAccountId(record.get(Headers.fromAccountId))
            .withToAccountId(record.get(Headers.toAccountId))
            .withCreatedAt(record.get(Headers.createdAt))
            .withAmount(record.get(Headers.amount))
            .withType(record.get(Headers.transactionType))
            .withRelatedTransaction(record.get(Headers.relatedTransaction))
        return transactionBuilder.build()
    }

    enum class Headers{
        transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction
    }
}