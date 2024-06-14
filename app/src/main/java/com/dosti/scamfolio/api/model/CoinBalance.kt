package com.dosti.scamfolio.api.model

data class CoinBalance(
    val username: String,
    val coinName: String,
    val quantity: Double,
    val price: Double,
    val isNegative: Boolean
    )