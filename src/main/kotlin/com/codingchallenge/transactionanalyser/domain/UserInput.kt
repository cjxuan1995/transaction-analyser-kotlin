package com.codingchallenge.transactionanalyser.domain

import java.time.LocalDateTime

data class UserInput(
    val accountId: String,
    val from: LocalDateTime,
    val to: LocalDateTime)