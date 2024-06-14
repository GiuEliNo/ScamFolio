package com.dosti.scamfolio.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(tableName = "CoinModelAPIDB", indices = [Index(value = arrayOf("symbol"), unique = true)])
data class CoinModelAPIDB(
    @PrimaryKey(autoGenerate = true) var autoID: Int,
    val id : String,
    val name: String,
    val symbol: String,
    val price: String?,
    val image: String,
    val price_change_percentage_24h : String,
    val current_price : String,
    var time_fetched: Long?
)