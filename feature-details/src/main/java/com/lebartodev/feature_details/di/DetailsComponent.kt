package com.lebartodev.feature_details.di

import com.lebartodev.core.di.CoreComponent
import com.lebartodev.core.di.scope.FeatureScope
import com.lebartodev.feature_details.ui.MovieDetailsFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [DetailsModule::class]
)
interface DetailsComponent {
    fun inject(trendingFragment: MovieDetailsFragment)
}