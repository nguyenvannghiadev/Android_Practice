/*
 * Copyright 2019, The Android Open Source Project
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
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val tonight = MutableLiveData<SleepNight?>()

    private val nights = database.getAllNights()
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)

    }

    // If tonight has not been set, then the START button should be visible.
    val startButtonVission = Transformations.map(tonight) {
        it == null
    }

    //If tonight has been set, then the STOP button should be visible.
    val stopButtonVission = Transformations.map(tonight) {
        it != null
    }

    //If there are any nights in the database, show the CLEAR button.
    val clearButtonVission = Transformations.map(nights) {
        it?.isNotEmpty()
    }


    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbarEvent

    // Khoi init khoi tao gia tri phai duoi khai bao bien
    init {
        initializeTonight()
    }

    fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    //
    private suspend fun insertItem(newNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(newNight)
        }
    }

    private suspend fun updateItem(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun clearData() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    //

    fun doneShowNackbar() {
        _showSnackbarEvent.value = false
    }
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }


    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insertItem(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }


    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            updateItem(oldNight)

            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            clearData()

            // And clear tonight since it's no longer in the database
            tonight.value = null
            // Show a snackbar message, because it's friendly.
            _showSnackbarEvent.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

