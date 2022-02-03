package com.example.currencylist

import android.app.Application
import com.example.currencylist.database.AppDatabase

class CurrencyApplication : Application(){
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}