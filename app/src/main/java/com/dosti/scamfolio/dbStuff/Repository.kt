package com.dosti.scamfolio.dbStuff

import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val dao: ScamfolioDao) {
    fun login(username : String, password : String) : User {
        return dao.loadByLogin(username, password)
    }

    fun signIn(username: String, password: String) {
        dao.insert(user = User(username, password))
    }

    fun getPurchasingList(username: String) : List<Purchasing>{
        return dao.getPurchasingList(username)
    }

    fun insertPurchasing(purchasing: Purchasing){
        CoroutineScope(Dispatchers.IO).launch{
            dao.insertPurchasing(purchasing)
        }
    }
}