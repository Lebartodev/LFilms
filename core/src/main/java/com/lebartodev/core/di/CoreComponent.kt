package com.lebartodev.core.di

import android.app.Application
import coil.ImageLoader
import com.lebartodev.core.db.dao.CreditsDao
import com.lebartodev.core.db.dao.GenresDao
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.network.MoviesService
import com.lebartodev.lib_utils.utils.AppCoroutineScope
import com.lebartodev.core.utils.ImageUrlProvider
import com.lebartodev.lib_authentication.di.AuthenticationModule
import com.lebartodev.lib_authentication.network.AccountService
import com.lebartodev.lib_authentication.repository.AccountManager
import com.lebartodev.lib_utils.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [CoreModule::class, AuthenticationModule::class])
interface CoreComponent {
    fun moviesService(): MoviesService
    fun moviesDao(): MoviesDao
    fun genresDao(): GenresDao
    fun creditsDao(): CreditsDao
    fun inject(provider: CoreComponentProvider)
    fun imageUrlProvider(): ImageUrlProvider
    fun imageLoader(): ImageLoader
    fun accountRepository(): AccountManager
    fun accountService(): AccountService
    fun coroutineScope(): AppCoroutineScope

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): CoreComponent
    }
}