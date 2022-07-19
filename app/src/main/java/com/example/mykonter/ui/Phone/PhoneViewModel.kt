package com.example.mykonter.ui.Phone

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mykonter.MainActivity
import com.example.mykonter.model.Phone
import com.example.mykonter.network.ApiStatus
import com.example.mykonter.network.PhoneApi
import com.example.mykonter.network.UpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PhoneViewModel : ViewModel() {

    private val data = MutableLiveData<List<Phone>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)

            try {
                data.postValue(PhoneApi.service.getPhone())
                status.postValue(ApiStatus.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)

            }
        }
    }

    fun getData(): LiveData<List<Phone>> = data
    fun getStatus(): LiveData<ApiStatus> = status
    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(30, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}