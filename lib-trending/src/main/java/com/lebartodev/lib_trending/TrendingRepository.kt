package com.lebartodev.lib_trending

import com.lebartodev.core.network.AsyncResult
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun refreshTrending()
    fun trending(): Flow<AsyncResult<List<TrendingData>>>
}