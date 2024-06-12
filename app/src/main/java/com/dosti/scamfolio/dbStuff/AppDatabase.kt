package com.dosti.scamfolio.dbStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.db.entities.Coin
import com.dosti.scamfolio.db.utils.ConverterDate
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.db.entities.User

@Database(entities = [User::class, Coin::class, Purchasing::class , CoinModelAPIDB::class], version = 1)
@TypeConverters(ConverterDate::class)
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
                  //  .createFromAsset("scamfolio.db")
                   // .allowMainThreadQueries()
                    .build()
            }
            return db as AppDatabase
        }
    }
}