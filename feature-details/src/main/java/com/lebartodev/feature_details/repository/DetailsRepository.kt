package com.lebartodev.feature_details.repository

import com.lebartodev.lib_utils.utils.AsyncResult
import com.lebartodev.lib.data.entity.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getMovieDetails(movieId: Long): Flow<AsyncResult<Movie>>
}