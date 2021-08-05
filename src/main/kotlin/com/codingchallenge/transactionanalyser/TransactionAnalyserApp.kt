package com.codingchallenge.transactionanalyser

import java.lang.IllegalArgumentException
import java.time.format.DateTimeFormatter

class TransactionAnalyserApp {
    fun runApp(){
        val transactionLoader = TransactionLoader(
            this::class.java.classLoader.getResourceAsStream("transactions.csv") ?: throw IllegalArgumentException("File not found")
        )
        val transactions = transactionLoader.loadTransactionsFromCsv()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val userInputReader = UserInputReader(formatter, System.`in`)
        val userInput = userInputReader.readUserInput()

        val transactionProcessor = TransactionProcessor()
        val filteredTransactions = transactionProcessor.filterTransactionsByUserInput(transactions, userInput)
        val relativeBalance = transactionProcessor.calculateRelativeBalance(filteredTransactions, userInput)

        println("Relative balance for the period is: $relativeBalance")
        println("Number of transactions included is: ${filteredTransactions.size}")
    }
}


fun main(args: Array<String>){
    TransactionAnalyserApp().runApp()
}