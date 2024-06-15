package com.dosti.scamfolio.api.model

data class Wallet(
    val username: String,
    val coinName: String,
    val quantity: Double,
    val balance: Double
)