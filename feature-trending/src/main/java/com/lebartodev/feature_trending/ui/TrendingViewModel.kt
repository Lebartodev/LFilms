package com.lebartodev.feature_trending.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.utils.SingleLiveEvent
import com.lebartodev.feature_trending.repository.TrendingData
import com.lebartodev.feature_trending.repository.TrendingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrendingViewModel @Inject constructor(
    private val repository: TrendingRepository
) : ViewModel() {
    private val trendingData = MutableLiveData<List<TrendingData>>()
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val trendingErrorData = SingleLiveEvent<String>()

    init {
        repository.trending()
            .mapLatest { response ->
                withContext(Dispatchers.Main) {
                    when (response) {
                        is AsyncResult.Success -> {
                            trendingData.value = response.data
                        }
                        is AsyncResult.Error -> {
                            trendingErrorData.value = response.error?.localizedMessage
                        }
                    }
                    loadingLiveData.value = response is AsyncResult.Loading
                }
            }.launchIn(viewModelScope)
        refreshTrending()
    }

    fun refreshTrending() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshTrending()
        }
    }

    fun trending(): LiveData<List<TrendingData>> = trendingData

    fun error(): LiveData<String?> = trendingErrorData

    fun loading(): LiveData<Boolean> = loadingLiveData
}