package com.dosti.scamfolio.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinModelAPI(
    @PrimaryKey(autoGenerate = true) var autoID: Int,
    val id : String,
    val name: String,
    val symbol: String,
    val price: String?,
    val image: String,
    val price_change_percentage_24h : String,
    val current_price : String
)

