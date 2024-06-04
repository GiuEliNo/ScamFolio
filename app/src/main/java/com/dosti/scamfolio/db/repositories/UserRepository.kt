package com.dosti.scamfolio.db.repositories

import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.dbStuff.ScamfolioDao


class UserRepository(private val dao: ScamfolioDao) {
    suspend fun insertUser(user: User) = dao.insert(user)

    suspend fun updateUser(user: User) = dao.update(user)

    suspend fun deleteUser(user: User) = dao.delete(user)

    fun login(username : String, password : String) : User = dao.loadByLogin(username, password)

}