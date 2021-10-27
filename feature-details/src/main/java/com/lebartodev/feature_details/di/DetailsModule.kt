package com.lebartodev.feature_details.di

import com.lebartodev.lib_utils.di.scope.FeatureScope
import com.lebartodev.feature_details.repository.DetailsRepository
import com.lebartodev.feature_details.repository.DetailsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DetailsModule {
    @FeatureScope
    @Binds
    fun bindsDetailsRepository(detailsRepository: DetailsRepositoryImpl): DetailsRepository
}