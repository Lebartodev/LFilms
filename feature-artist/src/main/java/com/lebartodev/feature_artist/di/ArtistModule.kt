package com.lebartodev.feature_artist.di

import com.lebartodev.core.di.scope.FeatureScope
import com.lebartodev.feature_artist.repository.ArtistRepository
import com.lebartodev.feature_artist.repository.ArtistRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface ArtistModule {
    @FeatureScope
    @Binds
    fun bindsDetailsRepository(detailsRepository: ArtistRepositoryImpl): ArtistRepository
}