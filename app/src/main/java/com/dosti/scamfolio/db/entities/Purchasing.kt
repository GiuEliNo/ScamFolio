package com.dosti.scamfolio.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "Purchasing",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["usernameUser"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        ),

        ForeignKey(
            entity = Coin::class,
            parentColumns = ["name"],
            childColumns = ["coinName"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )




    ]
)
data class Purchasing(
    @PrimaryKey(autoGenerate = true)
    val purchaseId: Int,
    @ColumnInfo(name = "coinName", index = true)
    val coinName: String,
    val quantity: Double,
    @ColumnInfo(name = "usernameUser", index = true)
    val usernameUser: String,
    val isNegative: Boolean,
    val transactionDate: String?
    )