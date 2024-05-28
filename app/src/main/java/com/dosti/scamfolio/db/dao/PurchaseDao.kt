package com.dosti.scamfolio.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dosti.scamfolio.db.entities.Purchase

@Dao
interface PurchaseDao {
    @Insert
    fun insertAll(purchases : List<Purchase>)

    @Insert
    fun insert(purchase: Purchase)

    @Update
    fun update(purchase: Purchase)

    @Delete
    fun delete(purchase: Purchase)
}