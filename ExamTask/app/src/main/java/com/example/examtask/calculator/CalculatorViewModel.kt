package com.example.examtask.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.examtask.database.CurrencyValueDao
import kotlinx.coroutines.*

class CalculatorViewModel(
    val dao: CurrencyValueDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun createAdapterForGroupSpinner(){

    }

    fun checkAll() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val values = dao.getAllValues()
                println(values.toString())
            }
        }
    }
}