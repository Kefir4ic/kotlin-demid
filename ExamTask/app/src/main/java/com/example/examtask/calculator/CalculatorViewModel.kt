package com.example.examtask.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.examtask.database.CurrencyValue
import com.example.examtask.database.CurrencyValueDao
import kotlinx.coroutines.*

class CalculatorViewModel(
    val dao: CurrencyValueDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var currencyFrom = MutableLiveData<Float>()
    private var currencyTo = MutableLiveData<Float>()


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun createAdapterForGroupSpinner(): LiveData<List<String>> {
         val allValues = dao.getAllValues()

        return allValues.map {createGroupsListForSpinner(it)}
    }

    private fun createGroupsListForSpinner(values: List<CurrencyValue>): List<String> {
        val groups: MutableList<String> = mutableListOf()
        for (value in values) {
            groups.add(value.currencyGroup)
        }
        return groups.distinct()
    }

    fun createAdapterForValuesSpinner(group: String): LiveData<List<String>> {
        val groupValues = dao.getGroup(group)
        return groupValues.map { createValuesNamesListForSpinner(it) }
    }

    private fun createValuesNamesListForSpinner(values: List<CurrencyValue>): List<String> {
        val names: MutableList<String> = mutableListOf()
        for (value in values) {
            names.add(value.currencyName)
        }
        return names.distinct()
    }

    fun onCalculate(selectedGroup: String, selectedFrom: String, selectedTo: String, valueFrom: String): String {
        if (selectedGroup == "")
            return "Empty group name!"
        if (selectedFrom == "")
            return "Empty from name!"
        if (selectedTo == "")
            return "Empty to name!"
        if (valueFrom == "")
            return "Empty value!"
        if (valueFrom.toFloatOrNull() == null || valueFrom.toIntOrNull() == null)
            return "Value is not Numeric!"
        return calculate(selectedGroup, selectedFrom, selectedTo, valueFrom.toFloat())
    }

    private fun calculate(selectedGroup: String, selectedFrom: String, selectedTo: String, valueFrom: Float): String {

        currencyFrom.value = getValue(selectedGroup, selectedFrom)
        currencyTo.value = getValue(selectedGroup, selectedTo)

        val valueCurrencyFrom = currencyFrom.value
        val valueCurrencyTo = currencyTo.value
        if (valueCurrencyFrom != null && valueCurrencyTo != null) {
            val total = valueFrom * valueCurrencyFrom / valueCurrencyTo
            return total.toString()
        }
        println(currencyFrom.value)
        println(valueCurrencyTo)
        return "No value in memory!"
    }


    private fun getValue(group: String, name: String): Float? {
        val groupValues = dao.getGroup(group)
        return groupValues.map { getCurrency(it, name) }.value
    }

    private fun getCurrency(values: List<CurrencyValue>, name: String): Float {
        for (value in values) {
            if (name == value.currencyName) {
                return value.currencyValue
            }
        }
        return 1.0F
    }
}