/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val dao: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

       private var viewModelJob = Job()

        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        private val nights = dao.getAllNights()

        private var tonight = MutableLiveData<SleepNight?>()

        val nightsString = Transformations.map(nights) { nights ->
            formatNights(nights, application.resources)
        }

        private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
        val navigateToSleepQuality: LiveData<SleepNight?>
            get() = _navigateToSleepQuality

        init {
                initTonight()
        }

        private fun initTonight(){
                uiScope.launch {
                        tonight.value = getTonightFromDao()
                }
        }

        private suspend fun getTonightFromDao(): SleepNight? {
                return withContext(Dispatchers.IO) {
                        var night = dao.getTonight()
                        if (night?.endTimeInMillis != night?.startTimeInMillis) {
                                night = null
                        }
                        return@withContext night
                }
        }

        override fun onCleared() {
                super.onCleared()
                viewModelJob.cancel()
        }

        fun onStartTracking() {
                uiScope.launch {
                        val newNight = SleepNight()
                        insert(newNight)
                        tonight.value = getTonightFromDao()
                }
        }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeInMillis = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            dao.clear()
        }
    }

    private suspend fun update(oldNight: SleepNight) {
        withContext(Dispatchers.IO) {
            dao.update(oldNight)
        }

    }

    private suspend fun insert(newNight: SleepNight) {
                withContext(Dispatchers.IO) {
                        dao.insert(newNight)
                }
        }

    fun doneNavigation() {
        _navigateToSleepQuality.value = null
    }
}

