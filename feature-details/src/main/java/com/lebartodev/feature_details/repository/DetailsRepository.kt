package com.lebartodev.feature_details.repository

import com.lebartodev.lib.data.entity.Movie

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: Long): Movie
}