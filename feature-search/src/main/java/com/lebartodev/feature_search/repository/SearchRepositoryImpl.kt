package com.lebartodev.feature_search.repository

import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.mapper.toEntity
import com.lebartodev.lib.data.network.MoviesResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
) : SearchRepository {
    private val searchStateFlow = MutableStateFlow<AsyncResult<List<MovieEntity>>>(AsyncResult.Loading())

    override fun result(): Flow<AsyncResult<List<MovieEntity>>> = searchStateFlow

    override suspend fun search(page: Int, term: String): MoviesResponse {
        return coroutineScope {
            val resultMovies = moviesService.searchMovies(term, page)
            moviesDao.upsertMovies(resultMovies.toMovies())
            resultMovies
        }
    }


    private fun MoviesResponse.toMovies(): List<MovieEntity> {
        return this.results?.map { it.toEntity() } ?: emptyList()
    }
}