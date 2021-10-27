package com.lebartodev.feature_artist.di

import com.lebartodev.core.di.CoreComponent
import com.lebartodev.lib_utils.di.scope.FeatureScope
import com.lebartodev.feature_artist.ui.ArtistDetailsFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ArtistModule::class]
)
interface ArtistComponent {
    fun inject(fragment: ArtistDetailsFragment)
}