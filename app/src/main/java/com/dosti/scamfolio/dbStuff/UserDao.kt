package com.dosti.scamfolio.dbStuff

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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

    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password")
    fun loadByLogin(username : String, password : String) : User
}