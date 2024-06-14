package com.dosti.scamfolio.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Coin
import com.dosti.scamfolio.db.entities.User

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

        /*ForeignKey(
            entity = CoinModelAPIDB::class,
            parentColumns = ["name"],
            childColumns = ["coinName"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )

         */


    ]
)
data class Purchasing(
    @PrimaryKey(autoGenerate = true)
    val purchaseId: Int,
    val coinName: String,
    val quantity: Double,
    val usernameUser: String,
    val isNegative: Boolean
)