package com.dosti.scamfolio.dbStuff

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dosti.scamfolio.api.model.CoinModelAPI
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

    @Insert
    fun insertCoinAPI(coin: MutableList<CoinModelAPI>)

    @Query("SELECT * FROM CoinModelAPI")
    fun loadAllCoin(): MutableList<CoinModelAPI>

    @Query("SELECT (SELECT COUNT(*) FROM CoinModelAPI) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT time_fetched FROM CoinModelAPI ORDER BY autoID LIMIT 1 ")
    fun chechFetchedDate(): Long

    @Query("DELETE FROM CoinModelAPI")
    suspend fun resetCoinList()

}
