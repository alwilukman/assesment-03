package com.example.mykonter.ui.promo

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
import com.example.mykonter.model.Promo
import com.example.mykonter.network.ApiStatus2
import com.example.mykonter.network.PromoApi
import com.example.mykonter.network.UpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PromoViewModel : ViewModel() {

    private val data = MutableLiveData<List<Promo>>()
    private val status = MutableLiveData<ApiStatus2>()
    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus2.LOADING)

            try {
                data.postValue(PromoApi.service.getPromo())
                status.postValue(ApiStatus2.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus2.FAILED)

            }
        }
    }

    fun getData(): LiveData<List<Promo>> = data
    fun getStatus(): LiveData<ApiStatus2> = status
    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}