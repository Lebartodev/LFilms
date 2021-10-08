package com.lebartodev.feature_search.di

import androidx.lifecycle.ViewModel
import com.lebartodev.core.di.FactoryModule
import com.lebartodev.core.di.scope.FeatureScope
import com.lebartodev.core.di.utils.ViewModelKey
import com.lebartodev.feature_search.repository.SearchRepository
import com.lebartodev.feature_search.repository.SearchRepositoryImpl
import com.lebartodev.feature_search.ui.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelsModule::class, FactoryModule::class])
interface SearchModule {
    @FeatureScope
    @Binds
    fun searchRepository(repository: SearchRepositoryImpl): SearchRepository
}


@Module
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(viewModel: SearchViewModel): ViewModel
}