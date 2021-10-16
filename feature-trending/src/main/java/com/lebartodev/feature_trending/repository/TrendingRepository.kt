package com.lebartodev.feature_trending.repository

import com.lebartodev.core.network.AsyncResult
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    suspend fun refreshTrending()
    fun trending(): Flow<AsyncResult<List<TrendingData>>>
}

