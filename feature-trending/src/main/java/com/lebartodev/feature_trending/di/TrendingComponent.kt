package com.lebartodev.feature_trending.di

import com.lebartodev.core.di.CoreComponent
import com.lebartodev.lib_utils.di.scope.FeatureScope
import com.lebartodev.feature_trending.ui.TrendingFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [TrendingModule::class]
)
interface TrendingComponent {
    fun inject(trendingFragment: TrendingFragment)
}