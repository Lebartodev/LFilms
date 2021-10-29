package com.lebartodev.feature_artist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lebartodev.lib_utils.utils.AsyncResult
import com.lebartodev.feature_artist.repository.ArtistRepository
import com.lebartodev.lib.data.entity.PersonEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ArtistViewModel constructor(
    private val creditId: String,
    private val detailsRepository: ArtistRepository
) : ViewModel() {
    private val artistLiveData = MutableLiveData<AsyncResult<PersonEntity>>()

    fun artist(): LiveData<AsyncResult<PersonEntity>> = artistLiveData

    init {
        detailsRepository.getArtistDetails(creditId)
            .onEach { artistLiveData.value = it }
            .launchIn(viewModelScope)
    }
}

class ArtistViewModelFactory @AssistedInject constructor(
    @Assisted("creditId") private val creditId: String,
    private val repository: ArtistRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArtistViewModel(creditId, repository) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("creditId") creditId: String): ArtistViewModelFactory
    }
}