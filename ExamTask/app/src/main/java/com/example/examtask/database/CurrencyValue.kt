package com.example.examtask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// описываем структуру таблицы
@Entity(tableName = "currency_value_table")
data class CurrencyValue(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var currencyId: Long = 0L,

    @ColumnInfo("currency_group")
    var currencyGroup: String = "",

    @ColumnInfo("currency_name")
    var currencyName: String = "",

    @ColumnInfo("currency_value")
    var currencyValue: Float = 1.0F
)