package com.lebartodev.core.di

import android.app.Application
import coil.ImageLoader
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.di.scope.AppScope
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.utils.ImageUrlProvider
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [CoreModule::class])
interface CoreComponent {
    fun moviesService(): MoviesService
    fun moviesDao(): MoviesDao
    fun inject(provider: CoreComponentProvider)
    fun imageUrlProvider(): ImageUrlProvider
    fun imageLoader(): ImageLoader

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): CoreComponent
    }
}