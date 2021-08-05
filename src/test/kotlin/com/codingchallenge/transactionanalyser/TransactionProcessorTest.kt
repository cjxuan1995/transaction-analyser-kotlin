package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.Transaction
import com.codingchallenge.transactionanalyser.domain.TransactionType
import com.codingchallenge.transactionanalyser.domain.UserInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

internal class TransactionProcessorTest{
    private val transactionProcessor = TransactionProcessor()

    @Test
    fun givenTransactions_WhenFilterTransactionsByAccount_ThenAllTransactionsLinkedToAccountReturned(){
        val account = getRandomIdString()
        val input = UserInput(account, LocalDateTime.MIN, LocalDateTime.MAX)

        val transactionFromAccount = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            LocalDateTime.now(), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactionToAccount = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            LocalDateTime.now(), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val unrelatedTransaction = Transaction(
            getRandomIdString(), getRandomIdString(), getRandomIdString(),
            LocalDateTime.now(), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactions = listOf(transactionFromAccount, transactionToAccount, unrelatedTransaction)

        val filteredTransactions = transactionProcessor.filterTransactionsByUserInput(transactions, input)

        // Transaction related to the accounts are returned
        assertEquals(filteredTransactions.size, 2)
        assertEquals(filteredTransactions[0].transactionId, transactionFromAccount.transactionId)
        assertEquals(filteredTransactions[1].transactionId, transactionToAccount.transactionId)
    }

    @Test
    fun givenTransactions_WhenFilterTransactionsByDate_ThenAllTransactionsFromPeriodReturned() {
        val account = getRandomIdString()
        val now = LocalDateTime.now()
        val input = UserInput(account, now.minusMinutes(5), now.plusMinutes(5)) // 10 minutes window
        val transactionFourMinutesBefore = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            now.minusMinutes(4), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactionFourMinutesAfter = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            now.plusMinutes(4), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactionTenMinutesBefore = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            now.minusMinutes(10), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactionTenMinutesAfter = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            now.plusMinutes(10), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactions = listOf(
            transactionFourMinutesBefore, transactionFourMinutesAfter,
            transactionTenMinutesBefore, transactionTenMinutesAfter
        )
        val filteredTransactions = transactionProcessor.filterTransactionsByUserInput(transactions, input)

        // Only the transactions within the 10 minutes window are returned
        assertEquals(filteredTransactions.size, 2)
        assertEquals(filteredTransactions[0].transactionId, transactionFourMinutesBefore.transactionId)
        assertEquals(filteredTransactions[1].transactionId, transactionFourMinutesAfter.transactionId)
    }


    @Test
    fun givenTransactions_WhenFilterTransactionsByReversal_ThenAllNoneReversedTransactionsReturned() {
        val account = getRandomIdString()
        val now = LocalDateTime.now()
        val input = UserInput(account, now.minusMinutes(5), now.plusMinutes(5))
        val paymentToBeReversed = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            now.minusMinutes(4), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val anotherPayment = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            now.plusMinutes(4), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val reversalOutsideFilterWindow = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            now.plusMinutes(10), BigDecimal.ONE, TransactionType.REVERSAL, paymentToBeReversed.transactionId
        )
        val paymentOutsideFilterWindow = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            now.plusMinutes(10), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val transactions =
            listOf(paymentToBeReversed, anotherPayment, reversalOutsideFilterWindow, paymentOutsideFilterWindow)
        val filteredTransactions = transactionProcessor.filterTransactionsByUserInput(transactions, input)

        // Only the second payment is returned because the first one has been reversed
        assertEquals(filteredTransactions.size, 1)
        assertEquals(filteredTransactions[0].transactionId, anotherPayment.transactionId)
    }

    @Test
    fun givenFilteredTransactions_WhenCalculateRelativeBalance_ThenCorrectBalanceReturned() {
        val account = getRandomIdString()
        val now = LocalDateTime.now()
        val input = UserInput(account, now.minusMinutes(5), now.plusMinutes(5))
        val paymentFromAccount = Transaction(
            getRandomIdString(), account, getRandomIdString(),
            now.minusMinutes(4), BigDecimal.ONE, TransactionType.PAYMENT, null
        )
        val paymentToAccount = Transaction(
            getRandomIdString(), getRandomIdString(), account,
            now.plusMinutes(4), BigDecimal.TEN, TransactionType.PAYMENT, null
        )
        val transactions = listOf(paymentFromAccount, paymentToAccount)
        val balance = transactionProcessor.calculateRelativeBalance(transactions, input)
        assertEquals(balance, BigDecimal(9)) // -1 + 10
    }

    private fun getRandomIdString(): String{
        return UUID.randomUUID().toString()
    }
}