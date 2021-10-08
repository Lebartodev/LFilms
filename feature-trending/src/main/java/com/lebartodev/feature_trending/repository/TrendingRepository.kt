package com.lebartodev.feature_trending.repository

import com.lebartodev.core.network.Response
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    suspend fun refreshTrending()
    fun trending(): Flow<Response<List<TrendingData>>>
}

