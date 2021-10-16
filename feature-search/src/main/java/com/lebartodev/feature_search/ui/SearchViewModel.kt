package com.lebartodev.feature_search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.utils.SingleLiveEvent
import com.lebartodev.feature_search.repository.SearchPagingDataSource
import com.lebartodev.feature_search.repository.SearchRepository
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val searchData = MutableLiveData<List<MovieEntity>>()
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val searchErrorData = SingleLiveEvent<String>()
    private val searchTerm = MutableStateFlow("")

    init {
        repository.result()
            .mapLatest { response ->
                withContext(Dispatchers.Main) {
                    when (response) {
                        is AsyncResult.Success -> {
                            searchData.value = response.data
                        }
                        is AsyncResult.Error -> {
                            searchErrorData.value = response.error?.localizedMessage
                        }
                    }
                    loadingLiveData.value = response is AsyncResult.Loading
                }
            }.launchIn(viewModelScope)
    }


    val result: Flow<PagingData<MovieEntity>> =
        searchTerm.flatMapLatest { term ->
            Pager(
                config = PagingConfig(pageSize = 1, prefetchDistance = 1),
                pagingSourceFactory = {
                    SearchPagingDataSource {
                        val result = repository.search(it, term)
                        SearchPagingDataSource.PageData(
                            maxPage = result.totalPages,
                            data = result.results?.map { it.toEntity() } ?: emptyList()
                        )
                    }
                }
            ).flow.cachedIn(viewModelScope)
        }


    fun search(term: String) {
        searchTerm.value = term
    }

    fun error(): LiveData<String?> = searchErrorData

    fun loading(): LiveData<Boolean> = loadingLiveData
}