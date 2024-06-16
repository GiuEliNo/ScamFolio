package com.dosti.scamfolio.db.repositories

import android.util.Log
import com.dosti.scamfolio.api.model.CoinBalance
import com.dosti.scamfolio.api.model.Wallet
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Coin
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.db.dao.ScamfolioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

   fun insertCoinForBalance(name:String, price: Double){
        CoroutineScope(Dispatchers.IO).launch{
            dao.insertCoinForBalance(coin= Coin(name,price))
        }
    }

    fun getCurrentPrice(name: String) :String?{
        return dao.getCurrentPrice(name)
    }

    fun getAllPurchasingForBalance(name: String) :List<CoinBalance> {
        return dao.getAllPurchasingForBalance(name)
    }

    suspend fun getQuantityCoinByiD(coinId: String, username: String) : Double{
        return withContext(Dispatchers.IO) {
            val negativeCoin = dao.getNegativeQuantityCoinByiD(coinId, username)
            val positiveCoin = dao.getPositiveQuantityCoinByiD(coinId, username)
            val total = positiveCoin - negativeCoin
            Log.d("Wallet Coin", total.toString())
            total
        }

    }

    fun getAllCoinSummary(name: String) : List<Wallet>{
        return dao.getUserCoinSummary(name)
    }

    fun updateCoinForBalance(name:String, price: Double) {
        return dao.updateCoinForBalance(Coin(name, price))
    }

    fun getCoinImage(coinId: String) : String{
        return dao.getCoinImage(coinId)
    }

    suspend fun getAllCoins(username: String): List<String> {
        return withContext(Dispatchers.IO) {
            dao.getAllCoins(username)
        }
    }

}