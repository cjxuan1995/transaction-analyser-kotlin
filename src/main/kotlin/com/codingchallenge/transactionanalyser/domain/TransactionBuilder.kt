package com.codingchallenge.transactionanalyser.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionBuilder(private val transactionId: String) {
    private lateinit var fromAccountId: String
    private lateinit var toAccountId: String
    private lateinit var createdAt: LocalDateTime
    private lateinit var amount: BigDecimal
    private lateinit var transactionType: TransactionType
    private var relatedTransaction: String? = null

    fun withFromAccountId(fromAccountId: String): TransactionBuilder{
        this.fromAccountId = fromAccountId
        return this
    }

    fun withToAccountId(toAccountId: String): TransactionBuilder{
        this.toAccountId = toAccountId
        return this
    }

    fun withCreatedAt(createdAt: String): TransactionBuilder{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        this.createdAt = LocalDateTime.parse(createdAt, formatter)
        return this
    }

    fun withAmount(amount: String): TransactionBuilder{
        this.amount = BigDecimal(amount)
        return this
    }

    fun withType(type: String): TransactionBuilder{
        this.transactionType = TransactionType.valueOf(type)
        return this
    }

    fun withRelatedTransaction(relatedTransaction: String?): TransactionBuilder{
        this.relatedTransaction = relatedTransaction
        return this
    }

    fun build(): Transaction{
        return Transaction(transactionId, fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction)
    }
}