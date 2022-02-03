package com.example.currencylist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency: Currency): Long

    @Query("SELECT * FROM currency ORDER BY id ASC")
    fun getAll(): Flow<List<Currency>>

    @Query("SELECT * FROM currency ORDER BY name ASC")
    fun getSortByName(): Flow<List<Currency>>

    @Query("SELECT * FROM currency WHERE name = :name ORDER BY id ASC")
    fun getByName(name: String): List<Currency>

}