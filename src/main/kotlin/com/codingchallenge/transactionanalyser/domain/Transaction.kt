package com.codingchallenge.transactionanalyser.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val transactionId: String,
    val fromAccountId: String,
    val toAccountId: String,
    val createdAt: LocalDateTime,
    val amount: BigDecimal,
    val transactionType: TransactionType,
    val relatedTransaction: String?
)