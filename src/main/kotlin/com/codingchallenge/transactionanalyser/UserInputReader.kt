package com.codingchallenge.transactionanalyser

import com.codingchallenge.transactionanalyser.domain.UserInput
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UserInputReader(private val formatter: DateTimeFormatter, private val inputStream: InputStream) {
    fun readUserInput(): UserInput{
        Scanner(inputStream).use {
            println("accountId: ")
            val account = it.nextLine()
            println("from: ")
            val from = LocalDateTime.parse(it.nextLine(), formatter)
            println("to: ")
            val to = LocalDateTime.parse(it.nextLine(), formatter)
            return UserInput(account, from, to)
        }
    }
}