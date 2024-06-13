package com.dosti.scamfolio.dbStuff

import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val dao: ScamfolioDao) {
    fun login(username : String, password : String) : User? {
        return dao.loadByLogin(username, password)
    }

    fun signIn(username: String, password: String, balance: Double) {
        dao.insert(user = User(username, password, balance))
    }

    fun getPurchasingList(username: String) : List<Purchasing>{
        return dao.getPurchasingList(username)
    }

    fun getBalance(username: String) : Double {
        return dao.getBalance(username)
    }

    fun insertPurchasing(purchasing: Purchasing){
        CoroutineScope(Dispatchers.IO).launch{
            dao.insertPurchasing(purchasing)
            if(purchasing.isNegative) {
                dao.decrementUserBalance(purchasing.usernameUser, purchasing.quantity)
            } else {
                dao.updateUserBalance(purchasing.usernameUser, purchasing.quantity)
            }

        }
    }

    fun checkUserExistence(username: String): Boolean{
        return dao.checkUserExistence(username)
    }

    fun insertCoinAPI(coin: MutableList<CoinModelAPIDB>){
        dao.insertCoinAPI(coin)
    }

    fun loadAllCoin() : MutableList<CoinModelAPIDB> {
        return dao.loadAllCoin()
    }

    fun isEmpty() : Boolean {
        return dao.isEmpty()
    }

    fun chechFetchedDate(): Long{
        return dao.chechFetchedDate()
    }

    fun resetCoinList(){
        CoroutineScope(Dispatchers.IO).launch{
            dao.resetCoinList()
        }
    }
}