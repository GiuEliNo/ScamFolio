package com.dosti.scamfolio.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dosti.scamfolio.db.User

@Dao
interface UserDao {
    @Insert
    fun insertAll(users : List<User>)

    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun loadAll() : Array<User>

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun loadByLogin(username : String, password : String) : User
}