package com.example.currencylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencylist.database.Currency
import com.example.currencylist.database.CurrencyDAO
import kotlinx.coroutines.flow.Flow

class CurrencyViewModel (private val currencyDAO:CurrencyDAO): ViewModel(){

    val clickedItem = MutableLiveData<Currency>()

    fun onListItemClick(currency: Currency) {
        clickedItem.value = currency
    }

    fun fullCurrency(): Flow<List<Currency>> = currencyDAO.getAll()
    fun currencySortByName(): Flow<List<Currency>> = currencyDAO.getSortByName()
}
class CurrencyViewModelFactory(private val currencyDAO: CurrencyDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(currencyDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
