package com.dosti.scamfolio.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coin(
    @PrimaryKey val name: String,
    val price: Double,
)