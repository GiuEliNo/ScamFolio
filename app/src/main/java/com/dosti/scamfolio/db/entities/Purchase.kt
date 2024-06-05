package com.dosti.scamfolio.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.util.Date

data class Purchase(
    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "name"
    )
    val transactionDate: Date,
    val transactionID: Long
)
