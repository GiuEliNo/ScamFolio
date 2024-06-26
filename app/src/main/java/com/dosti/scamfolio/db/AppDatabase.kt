package com.dosti.scamfolio.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.dosti.scamfolio.db.dao.ScamfolioDao
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Coin
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User

@Database(entities = [User::class, Coin::class, Purchasing::class , CoinModelAPIDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ScamfolioDao(): ScamfolioDao

    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (db == null) {
                db = databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "scamfolio.db"
                )
                    .build()
            }
            return db as AppDatabase
        }
    }
}