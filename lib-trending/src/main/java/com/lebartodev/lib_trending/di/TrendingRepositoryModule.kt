package com.lebartodev.lib_trending.di

import com.lebartodev.lib_trending.TrendingRepository
import com.lebartodev.lib_trending.TrendingRepositoryImpl
import com.lebartodev.lib_utils.di.scope.AppScope
import dagger.Binds
import dagger.Module

@Module
interface TrendingRepositoryModule {
    @AppScope
    @Binds
    fun bindsTrendingRepository(trendingRepositoryImpl: TrendingRepositoryImpl): TrendingRepository
}
