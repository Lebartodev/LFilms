package com.lebartodev.feature_details.repository

import com.lebartodev.lib.data.entity.MovieEntity

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: Long): MovieEntity
}