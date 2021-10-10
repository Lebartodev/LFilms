package com.lebartodev.feature_details.repository

import com.lebartodev.core.db.dao.GenresDao
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.MoviesService
import com.lebartodev.lib.data.entity.Movie
import com.lebartodev.lib.data.mapper.toEntity
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val moviesDao: MoviesDao,
    private val genresDao: GenresDao
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Long): Movie {
        return coroutineScope {
            val details = service.getMovieDetails(movieId)
            val result = details.toEntity()
            moviesDao.insertMovie(result)
            genresDao.upsertGenres(result.id, result.genres)
            result
        }
    }
}