package com.lebartodev.feature_details.di

import com.lebartodev.core.di.scope.FeatureScope
import com.lebartodev.feature_details.repository.DetailsRepository
import com.lebartodev.feature_details.repository.DetailsRepositoryImpl
import com.lebartodev.feature_details.ui.DetailsViewModel
import dagger.Binds
import dagger.Module

@Module
interface DetailsModule {
    @FeatureScope
    @Binds
    fun bindsDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository
}