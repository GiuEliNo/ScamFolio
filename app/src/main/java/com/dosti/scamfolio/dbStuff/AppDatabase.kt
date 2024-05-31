package com.dosti.scamfolio.dbStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dosti.scamfolio.db.dao.PurchaseDao
import com.dosti.scamfolio.db.utils.ConverterDate
import com.dosti.scamfolio.db.entities.Purchase

@Database(entities = [User::class], version = 1)
@TypeConverters(ConverterDate::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (db == null) {
                db = databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "scamfolio.db"
                )
                    .createFromAsset("scamfolio.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return db as AppDatabase
        }
    }
}