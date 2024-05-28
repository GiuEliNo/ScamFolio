package com.dosti.scamfolio.db.repositories

import androidx.lifecycle.LiveData
import com.dosti.scamfolio.db.User
import com.dosti.scamfolio.db.dao.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insert(user)

    suspend fun updateUser(user: User) = userDao.update(user)

    suspend fun deleteUser(user: User) = userDao.delete(user)

    fun login(username : String, password : String) : User = userDao.loadByLogin(username, password)

}