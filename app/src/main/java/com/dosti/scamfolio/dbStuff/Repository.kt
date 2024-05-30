package com.dosti.scamfolio.dbStuff

class Repository(private val dao: UserDao) {
    fun login(username : String, password : String) : User {
        return dao.loadByLogin(username, password)
    }
}