package com.lebartodev.feature_trending.repository

import android.util.Log
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.network.Response
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
    private val TAG = "TrendingRepository"
    private val trendingStateFlow =
        MutableStateFlow<Response<List<TrendingData>>>(Response.Loading())

    override fun trending(): Flow<Response<List<TrendingData>>> = trendingStateFlow

    override suspend fun refreshTrending() {
        try {
            coroutineScope {
                trendingStateFlow.value = Response.Loading()
                val result = TrendingCategory.values()
                    .map { async { TrendingData(it, loadTrendingCategory(it).toMovies()) } }
                    .awaitAll()
                    .filter { it.movies.isNotEmpty() }

                moviesDao.upsertMovies(result.map { it.movies }.flatten())

                trendingStateFlow.value = Response.Success(result)
            }
        } catch (e: Exception) {
            Log.e(TAG, "refreshTrending: ", e)
            trendingStateFlow.value = Response.Error(e.localizedMessage)
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