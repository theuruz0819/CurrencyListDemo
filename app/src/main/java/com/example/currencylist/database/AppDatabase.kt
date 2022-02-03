package com.example.currencylist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Currency::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "example_database")
                    //.createFromAsset("database/example_currency.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
