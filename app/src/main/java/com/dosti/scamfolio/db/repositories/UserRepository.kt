package com.dosti.scamfolio.db.repositories

import com.dosti.scamfolio.dbStuff.User
import com.dosti.scamfolio.dbStuff.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insert(user)

    suspend fun updateUser(user: User) = userDao.update(user)

    suspend fun deleteUser(user: User) = userDao.delete(user)

    fun login(username : String, password : String) : User = userDao.loadByLogin(username, password)

}