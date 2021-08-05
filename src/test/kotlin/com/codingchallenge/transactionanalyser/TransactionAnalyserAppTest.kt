package com.codingchallenge.transactionanalyser

import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class TransactionAnalyserAppTest{
    private val transactionAnalyserApp = TransactionAnalyserApp()

    @Test
    fun integrationTest(){
        // Set up input/output for testing
        val sysIn = System.`in`
        val sysOut = System.out
        val inputParams = arrayOf("ACC334455", "20/10/2018 12:00:00", "20/10/2018 19:00:00")
        val inputStream = ByteArrayInputStream(
            inputParams.joinToString(System.lineSeparator()).toByteArray()
        )
        System.setIn(inputStream)
        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        transactionAnalyserApp.runApp()

        assertEquals(outputStreamCaptor.toString(), """
     accountId: 
     from: 
     to: 
     Relative balance for the period is: -25.00
     Number of transactions included is: 1
     
     """.trimIndent()
        )

        // Restore input/output to System.in/out
        System.setIn(sysIn)
        System.setOut(sysOut)
    }
}