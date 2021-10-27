package com.lebartodev.feature_trending.di

import androidx.lifecycle.ViewModel
import com.lebartodev.core.di.FactoryModule
import com.lebartodev.lib_utils.di.scope.FeatureScope
import com.lebartodev.core.di.utils.ViewModelKey
import com.lebartodev.feature_trending.repository.TrendingRepository
import com.lebartodev.feature_trending.repository.TrendingRepositoryImpl
import com.lebartodev.feature_trending.ui.TrendingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelsModule::class, FactoryModule::class])
interface TrendingModule {
    @FeatureScope
    @Binds
    fun bindsTrendingRepository(trendingRepositoryImpl: TrendingRepositoryImpl): TrendingRepository
}


@Module
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel::class)
    fun demoViewModel(viewModel: TrendingViewModel): ViewModel
}