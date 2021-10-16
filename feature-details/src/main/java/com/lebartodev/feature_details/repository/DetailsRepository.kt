package com.lebartodev.feature_details.repository

import com.lebartodev.core.network.AsyncResult
import com.lebartodev.lib.data.entity.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getMovieDetails(movieId: Long): Flow<AsyncResult<Movie>>
}