package com.example.examtask.addvalue

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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


    fun onAddValue(currencyGroup: String, currencyName: String, currencyValue: String): String {
        if (currencyGroup == "")
            return "Empty group name!"
        if (currencyName == "")
            return "Empty value name!"
        if (currencyValue == "")
            return "Empty value!"
        if (currencyValue.toFloatOrNull() == null)
            return "Value is not Numeric!"
        addValue(currencyGroup, currencyName, currencyValue.toFloat())
        return "Successfully added!"
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