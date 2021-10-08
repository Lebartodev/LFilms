package com.lebartodev.feature_details.ui

import androidx.lifecycle.*
import com.lebartodev.feature_details.repository.DetailsRepository
import com.lebartodev.lib.data.entity.MovieEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    private val movieId: Long,
    private val detailsRepository: DetailsRepository
) : ViewModel() {
    private val movieLiveData = MutableLiveData<MovieEntity>()

    init {
        viewModelScope.launch {
            val result = detailsRepository.getMovieDetails(movieId)
            movieLiveData.value = result
        }
    }

    fun movie(): LiveData<MovieEntity> = movieLiveData
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