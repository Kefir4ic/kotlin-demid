package com.example.examtask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyValueDao {

    @Insert
    fun insert(value: CurrencyValue)

    @Query("SELECT * FROM currency_value_table WHERE currency_group = :group")
    fun getGroup(group: String): LiveData<List<CurrencyValue>>

    @Query("SELECT * FROM currency_value_table")
    fun getAllValues(): List<CurrencyValue>
}