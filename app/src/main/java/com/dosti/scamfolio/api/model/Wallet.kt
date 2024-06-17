package com.dosti.scamfolio.api.model

data class Wallet(
    val username: String,
    val coinName: String,
    val totalQuantity: Double,
    val balance: Double
)