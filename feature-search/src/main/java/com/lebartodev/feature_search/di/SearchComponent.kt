package com.lebartodev.feature_search.di

import com.lebartodev.core.di.CoreComponent
import com.lebartodev.lib_utils.di.scope.FeatureScope
import com.lebartodev.feature_search.ui.SearchFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [SearchModule::class]
)
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}