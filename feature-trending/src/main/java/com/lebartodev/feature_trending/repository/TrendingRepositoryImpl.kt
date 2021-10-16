package com.lebartodev.feature_trending.repository

import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.utils.loadIntoStateFlow
import com.lebartodev.lib.data.mapper.toMovies
import com.lebartodev.lib.data.network.MoviesResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
) : TrendingRepository {
    private val trendingStateFlow =
        MutableStateFlow<AsyncResult<List<TrendingData>>>(AsyncResult.Loading())

    override fun trending(): Flow<AsyncResult<List<TrendingData>>> = trendingStateFlow

    @SuppressWarnings("TooGenericExceptionCaught")
    override suspend fun refreshTrending() {
        suspend {
            coroutineScope {
                TrendingCategory.values()
                    .map { async { TrendingData(it, loadTrendingCategory(it).toMovies()) } }
                    .awaitAll()
                    .filter { it.movies.isNotEmpty() }
                    .also { moviesDao.upsertMovies(it.map { it.movies }.flatten()) }
            }
        }.loadIntoStateFlow(trendingStateFlow)
    }

    private suspend fun loadTrendingCategory(trendingCategory: TrendingCategory): MoviesResponse {
        return when (trendingCategory) {
            TrendingCategory.POPULAR -> moviesService.getPopular()
            TrendingCategory.NOW_PLAYING -> moviesService.getNowPlaying()
            TrendingCategory.LATEST -> moviesService.getLatest()
            TrendingCategory.TOP_RATED -> moviesService.getTopRated()
            TrendingCategory.UPCOMING -> moviesService.getUpcoming()
        }
    }
}