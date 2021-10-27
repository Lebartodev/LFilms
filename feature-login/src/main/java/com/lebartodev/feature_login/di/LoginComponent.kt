package com.lebartodev.feature_login.di

import com.lebartodev.core.di.CoreComponent
import com.lebartodev.feature_login.ui.LoginFragment
import com.lebartodev.lib_utils.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [LoginModule::class]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}