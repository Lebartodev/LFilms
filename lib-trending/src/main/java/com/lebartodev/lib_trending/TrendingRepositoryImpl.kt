package com.lebartodev.lib_trending

import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.utils.loadIntoStateFlow
import com.lebartodev.lib.data.mapper.toMovies
import com.lebartodev.lib.data.network.MoviesResponse
import com.lebartodev.lib_utils.utils.AppCoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
    private val appCoroutineScope: AppCoroutineScope
) : TrendingRepository {
    private val trendingStateFlow =
        MutableStateFlow<AsyncResult<List<TrendingData>>>(AsyncResult.Loading())

    init {
        refreshTrending()
    }

    override fun trending(): Flow<AsyncResult<List<TrendingData>>> = trendingStateFlow

    @SuppressWarnings("TooGenericExceptionCaught")
    override fun refreshTrending() {
        appCoroutineScope.launch {
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