package com.dosti.scamfolio.db.dao

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDao {
    @Insert
    fun insertUser()
}