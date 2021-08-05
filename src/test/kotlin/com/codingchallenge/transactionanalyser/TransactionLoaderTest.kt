package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.TransactionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class TransactionLoaderTest{
    private val transactionLoader = TransactionLoader(
        this::class.java.classLoader.getResourceAsStream("transactions.csv") ?: throw IllegalArgumentException("File not found")
    )
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    @Test
    fun givenCsv_WhenLoadTransactionsFromCsv_ThenTheExpectedTransactionsReturned(){
        val transactions = transactionLoader.loadTransactionsFromCsv()
        assertEquals(transactions.size, 5);

        assertEquals(transactions[0].transactionId, "TX10001")
        assertEquals(transactions.get(0).fromAccountId, "ACC334455")
        assertEquals(transactions[0].transactionId, "TX10001")
        assertEquals(transactions[0].fromAccountId, "ACC334455")
        assertEquals(transactions[0].toAccountId, "ACC778899")
        assertEquals(transactions[0].createdAt, LocalDateTime.parse("20/10/2018 12:47:55", formatter))
        assertEquals(transactions[0].amount.compareTo(BigDecimal.valueOf(25.00)), 0)
        assertEquals(transactions[0].transactionType, TransactionType.PAYMENT)
        assertNull(transactions[0].relatedTransaction) // PAYMENT transaction should have null relatedTransaction


        assertEquals(transactions[1].transactionId, "TX10002")
        assertEquals(transactions[1].fromAccountId, "ACC334455")
        assertEquals(transactions[1].toAccountId, "ACC998877")
        assertEquals(transactions[1].createdAt, LocalDateTime.parse("20/10/2018 17:33:43", formatter))
        assertEquals(transactions[1].amount.compareTo(BigDecimal.valueOf(10.50)), 0)
        assertEquals(transactions[1].transactionType, TransactionType.PAYMENT)
        assertNull(transactions[1].relatedTransaction)

        assertEquals(transactions[3].transactionId, "TX10004")
        assertEquals(transactions[3].fromAccountId, "ACC334455")
        assertEquals(transactions[3].toAccountId, "ACC998877")
        assertEquals(transactions[3].createdAt, LocalDateTime.parse("20/10/2018 19:45:00", formatter))
        assertEquals(transactions[3].amount.compareTo(BigDecimal.valueOf(10.50)), 0)
        assertEquals(transactions[3].transactionType, TransactionType.REVERSAL)
        assertEquals(
            transactions[3].relatedTransaction,
            "TX10002"
        ) // REVERSAL transaction should have non-null relatedTransaction

    }
}