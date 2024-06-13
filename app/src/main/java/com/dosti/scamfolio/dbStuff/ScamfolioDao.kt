package com.dosti.scamfolio.dbStuff

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User

@Dao
interface ScamfolioDao {
    @Insert
    fun insertAll(users: List<User>)

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun loadAll(): Array<User>

    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password LIMIT 1")
    fun loadByLogin(username: String, password: String): User?

    @Query("SELECT * FROM Purchasing WHERE usernameUser LIKE :username ")
    fun getPurchasingList(username: String): List<Purchasing>

    @Insert
    fun insertPurchasing(purchasing: Purchasing)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username LIKE :username LIMIT 1)")
    fun checkUserExistence(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinAPI(coin: MutableList<CoinModelAPIDB>)

    @Query("SELECT * FROM CoinModelAPIDB")
    fun loadAllCoin(): MutableList<CoinModelAPIDB>

    @Query("SELECT (SELECT COUNT(*) FROM CoinModelAPIDB) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT time_fetched FROM CoinModelAPIDB ORDER BY autoID LIMIT 1 ")
    fun chechFetchedDate(): Long

    @Query("DELETE FROM CoinModelAPIDB")
    suspend fun resetCoinList()

    @Query("UPDATE User SET balance = balance + :qty WHERE Username LIKE :username ")
    fun updateUserBalance(username: String, qty: Double)
}
