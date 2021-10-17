package com.lebartodev.feature_details.repository

import com.lebartodev.core.db.dao.CreditsDao
import com.lebartodev.core.db.dao.GenresDao
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.network.MoviesService
import com.lebartodev.lib.data.entity.Movie
import com.lebartodev.lib.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val moviesDao: MoviesDao,
    private val genresDao: GenresDao,
    private val creditsDao: CreditsDao
) : DetailsRepository {

    override fun getMovieDetails(movieId: Long): Flow<AsyncResult<Movie>> {
        return flow {
            val databaseDetails = moviesDao.getById(movieId)
            emit(AsyncResult.Loading(databaseDetails))
            val details = service.getMovieDetails(movieId)
            val credits = service.getMovieCredits(movieId)
            val cast = credits.cast?.map { it.toEntity(movieId) } ?: listOf()
            val result = details.toEntity().also { it.cast = cast }
            moviesDao.insertMovie(result)
            genresDao.upsertGenres(result.id, result.genres)
            creditsDao.upsertCast(cast)
            emit(AsyncResult.Success(result))
        }
    }
}