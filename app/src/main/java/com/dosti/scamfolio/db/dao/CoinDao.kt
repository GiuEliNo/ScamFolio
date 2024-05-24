package com.dosti.scamfolio.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dosti.scamfolio.db.entities.Coin

@Dao
interface CoinDao {
    @Insert
    fun insertAll(coins : List<Coin>)

    @Insert
    fun insert(coin: Coin)

    @Update
    fun update(coin: Coin)

    @Delete
    fun delete(coin: Coin)

    @Query("SELECT * FROM Coin")
    fun loadAll() : Array<Coin>

    @Query("SELECT * FROM Coin WHERE name LIKE :name")
    fun loadByName(name : String) : Coin
}