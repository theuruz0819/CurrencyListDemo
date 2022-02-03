package com.example.currencylist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.currencylist.database.Currency
import com.example.currencylist.viewmodel.CurrencyViewModel
import com.example.currencylist.viewmodel.CurrencyViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class DemoActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels{
        CurrencyViewModelFactory(
            (application as CurrencyApplication).database.currencyDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.clickedItem.observe(this) {
            // item click event observe by activity
            Log.i("DemoActivity", "click" + it.name)
        }

        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            // initial Db data on IO thread
            initialDbData()
        }
    }
    private fun initialDbData(){
        var jsonString: String = ""
        val gson = Gson()
        try {
            // load sample data from assets and save to DB
            jsonString = assets.open("initialDb.Json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Currency>>() {}.type
            val currencyList: List<Currency> = gson.fromJson(jsonString, listType)
            currencyList.forEach {
                (application as CurrencyApplication).database.currencyDao().insert(it)
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}