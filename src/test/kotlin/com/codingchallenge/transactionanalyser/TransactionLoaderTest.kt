package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.TransactionBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TransactionLoaderTest{
    private val transactionLoader = TransactionLoader(
        this::class.java.classLoader.getResourceAsStream("transactions.csv") ?: throw IllegalArgumentException("File not found")
    )

    @Nested
    inner class TransactionsLoadedFromCsv {
        private val transactions = transactionLoader.loadTransactionsFromCsv()

        @Test
        fun `should load all the transactions`(){
            assertEquals(transactions.size, 5)
        }

        @Test
        fun `should load the first payment transaction`(){
            val expectedTransaction = TransactionBuilder("TX10001")
                .withFromAccountId("ACC334455")
                .withToAccountId("ACC778899")
                .withCreatedAt("20/10/2018 12:47:55")
                .withAmount("25.00")
                .withType("PAYMENT")
                .withRelatedTransaction(null)
                .build()

            assertEquals(expectedTransaction, transactions[0])
        }

        @Test
        fun `should load the second payment transaction`(){
            val expectedTransaction = TransactionBuilder("TX10002")
                .withFromAccountId("ACC334455")
                .withToAccountId("ACC998877")
                .withCreatedAt("20/10/2018 17:33:43")
                .withAmount("10.50")
                .withType("PAYMENT")
                .withRelatedTransaction(null)
                .build()

            assertEquals(expectedTransaction, transactions[1])
        }

        @Test
        fun `should load the fourth reversal transaction`(){
            val expectedTransaction = TransactionBuilder("TX10004")
                .withFromAccountId("ACC334455")
                .withToAccountId("ACC998877")
                .withCreatedAt("20/10/2018 19:45:00")
                .withAmount("10.50")
                .withType("REVERSAL")
                .withRelatedTransaction("TX10002")
                .build()

            assertEquals(expectedTransaction, transactions[3])
        }
    }
}