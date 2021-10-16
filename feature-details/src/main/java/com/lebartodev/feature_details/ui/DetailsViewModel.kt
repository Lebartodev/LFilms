package com.lebartodev.feature_details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.feature_details.repository.DetailsRepository
import com.lebartodev.lib.data.entity.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsViewModel constructor(
    private val movieId: Long,
    private val detailsRepository: DetailsRepository
) : ViewModel() {
    private val movieLiveData = MutableLiveData<AsyncResult<Movie>>()

    fun movie(): LiveData<AsyncResult<Movie>> = movieLiveData

    init {
        detailsRepository.getMovieDetails(movieId)
            .onEach { movieLiveData.value = it }
            .launchIn(viewModelScope)
    }
}

class DetailsViewModelFactory @AssistedInject constructor(
    @Assisted("movieId") private val movieId: Long,
    private val detailsRepository: DetailsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(movieId, detailsRepository) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("movieId") movieId: Long): DetailsViewModelFactory
    }
}