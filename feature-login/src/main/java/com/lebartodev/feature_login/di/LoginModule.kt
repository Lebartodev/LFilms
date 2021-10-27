package com.lebartodev.feature_login.di

import androidx.lifecycle.ViewModel
import com.lebartodev.core.di.FactoryModule
import com.lebartodev.core.di.utils.ViewModelKey
import com.lebartodev.feature_login.ui.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelsModule::class, FactoryModule::class])
interface LoginModule

@Module
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun demoViewModel(viewModel: LoginViewModel): ViewModel
}