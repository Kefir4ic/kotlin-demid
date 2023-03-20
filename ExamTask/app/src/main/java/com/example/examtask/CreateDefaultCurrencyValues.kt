package com.example.examtask


import com.example.examtask.database.CurrencyValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun generateDefaultValues(json: String): Array<CurrencyValue> {
    val gson = Gson()

    val values = object : TypeToken<Array<CurrencyValue>>() {}.type

    return gson.fromJson(json, values)
}
