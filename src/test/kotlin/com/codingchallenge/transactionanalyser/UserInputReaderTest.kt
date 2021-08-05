package com.codingchallenge.transactionanalyser

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.arrayOf

internal class UserInputReaderTest{
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    @Test
    fun givenValidInputParameters_WhenReadUserInput_ThenExpectedObjectReturned() {
        val inputParams = arrayOf("ACC334455", "20/10/2018 12:00:00", "20/10/2018 19:00:00")
        val inputStream = ByteArrayInputStream(
            inputParams.joinToString(System.lineSeparator()).toByteArray()
        )
        val userInputReader = UserInputReader(formatter, inputStream)
        val (accountId, from, to) = userInputReader.readUserInput()
        assertEquals(accountId, "ACC334455")
        assertEquals(from, LocalDateTime.parse("20/10/2018 12:00:00", formatter))
        assertEquals(to, LocalDateTime.parse("20/10/2018 19:00:00", formatter))
    }
}