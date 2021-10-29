package com.lebartodev.feature_trending.di

import androidx.lifecycle.ViewModel
import com.lebartodev.core.di.FactoryModule
import com.lebartodev.core.di.utils.ViewModelKey
import com.lebartodev.feature_trending.ui.TrendingViewModel
import com.lebartodev.lib_utils.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelsModule::class, FactoryModule::class])
interface TrendingModule


@Module
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel::class)
    fun demoViewModel(viewModel: TrendingViewModel): ViewModel
}