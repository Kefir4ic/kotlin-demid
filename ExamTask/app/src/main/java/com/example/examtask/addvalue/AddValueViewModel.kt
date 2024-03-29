package com.example.examtask.addvalue

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.examtask.database.CurrencyValue
import com.example.examtask.database.CurrencyValueDao
import kotlinx.coroutines.*


class AddValueViewModel(
    val dao: CurrencyValueDao,
    application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun createGroupValuesNames(group: String): LiveData<List<String>> {
        val groupValues = dao.getGroup(group)
        return groupValues.map { _createListOfNames(it) }
    }

    private fun _createListOfNames(values: List<CurrencyValue>): List<String> {
        val names: MutableList<String> = mutableListOf()
        for (value in values) {
            names.add(value.currencyName)
        }
        return names.distinct()
    }


    fun onAddValue(currencyGroup: String, currencyName: String, currencyValue: String, namesInGroup: List<String>): Int {
        if (currencyGroup == "")
            return 1
        if (currencyName == "")
            return 2
        if (currencyValue == "")
            return 3
        if (currencyValue.toFloatOrNull() == null)
            return 4
        if (currencyName in namesInGroup)
            return 5
        addValue(currencyGroup, currencyName, currencyValue.toFloat())
        return 0
    }

    private fun addValue(currencyGroup: String, currencyName: String, currencyValue: Float){
        uiScope.launch {
            val newValue = CurrencyValue(currencyGroup = currencyGroup,
                currencyName = currencyName,
                currencyValue = currencyValue)
            insert(newValue)
        }
    }

    private suspend fun insert(newValue: CurrencyValue) {
        withContext(Dispatchers.IO) {
            dao.insert(newValue)
        }
    }
}