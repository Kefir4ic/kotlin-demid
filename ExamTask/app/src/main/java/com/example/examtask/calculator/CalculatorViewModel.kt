package com.example.examtask.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.examtask.database.CurrencyValue
import com.example.examtask.database.CurrencyValueDao
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CalculatorViewModel(
    val dao: CurrencyValueDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

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

    fun onCalculate(selectedGroup: String, selectedFrom: String, selectedTo: String,
                    valueFrom: String, roundingString: String, values: List<Double>): String {
        var rounding = 2
        if (selectedGroup == "")
            return "Empty group name!"
        if (selectedFrom == "")
            return "Empty from name!"
        if (selectedTo == "")
            return "Empty to name!"
        if (valueFrom == "")
            return "Empty value!"
        if (valueFrom.toFloatOrNull() == null)
            return "Value is not Numeric!"
        if (values.isEmpty())
            return "No value in memory!"
        if (roundingString != "")
            rounding = roundingString.toInt()
        return calculate(valueFrom.toDouble(), values, selectedTo, rounding)
    }

    private fun calculate(valueFrom: Double, values: List<Double>, selectedTo: String, rounding: Int): String {
        val numerator = (valueFrom * values[0]).toBigDecimal()
        val result = numerator.div(values[1].toBigDecimal())
        val total = result.setScale(rounding, RoundingMode.HALF_UP).toFloat()
        val formatString = "%.${rounding}f"
        return "${String.format(formatString, total)} $selectedTo"
    }

    fun getValues(group: String, selectedFrom: String, selectedTo: String): LiveData<List<Double>> {
        val groupValues = dao.getGroup(group)
        return groupValues.map { getCurrency(it, selectedFrom, selectedTo) }
    }

    private fun getCurrency(values: List<CurrencyValue>, selectedFrom: String, selectedTo: String): List<Double> {
        val valuesToFrom: MutableList<Double> = mutableListOf()
        for (value in values) {
            if (selectedFrom == value.currencyName) {
                valuesToFrom.add(value.currencyValue.toDouble())
            }
        }
        for (value in values) {
            if (selectedTo == value.currencyName) {
                valuesToFrom.add(value.currencyValue.toDouble())
            }
        }
        return valuesToFrom
    }
}