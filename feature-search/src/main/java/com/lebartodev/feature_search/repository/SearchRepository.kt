package com.lebartodev.feature_search.repository

import com.lebartodev.core.network.AsyncResult
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.network.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(page: Int, term: String): MoviesResponse
    fun result(): Flow<AsyncResult<List<MovieEntity>>>
}

