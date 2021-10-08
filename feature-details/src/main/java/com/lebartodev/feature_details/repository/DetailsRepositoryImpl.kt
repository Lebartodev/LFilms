package com.lebartodev.feature_details.repository

import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.MoviesService
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.mapper.toEntity
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val dao: MoviesDao
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Long): MovieEntity {
        return coroutineScope {
            val details = service.getMovieDetails(movieId)
            val result = details.toEntity()
            dao.insertMovie(result)
            result
        }
    }
}