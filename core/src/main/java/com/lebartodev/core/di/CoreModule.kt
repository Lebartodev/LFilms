package com.lebartodev.core.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import coil.ImageLoader
import com.lebartodev.core.BuildConfig
import com.lebartodev.core.db.AppDatabase
import com.lebartodev.core.db.dao.CreditsDao
import com.lebartodev.core.db.dao.GenresDao
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.core.di.scope.AppScope
import com.lebartodev.core.network.MoviesService
import com.lebartodev.core.utils.ImageUrlProvider
import com.lebartodev.core.utils.ImageUrlProviderImpl
import com.lebartodev.core.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [NetworkModule::class, DatabaseModule::class, FactoryModule::class, ImageProviderModule::class])
interface CoreModule

@Module
class DatabaseModule {
    @AppScope
    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "database.db").build()

    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MoviesDao = appDatabase.moviesDao()

    @Provides
    fun provideGenresDao(appDatabase: AppDatabase): GenresDao = appDatabase.genresDao()

    @Provides
    fun provideCreditsDao(appDatabase: AppDatabase): CreditsDao = appDatabase.creditsDao()
}

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @AppScope
    @Provides
    fun provideImageLoader(application: Application): ImageLoader {
        return ImageLoader.Builder(application)
            .availableMemoryPercentage(MEMORY_PERCENTAGE)
            .crossfade(true)
            .build()
    }

    companion object {
        const val MEMORY_PERCENTAGE = 0.25
    }
}

@Module
interface FactoryModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
interface ImageProviderModule {
    @Binds
    fun bindViewModelFactory(provider: ImageUrlProviderImpl): ImageUrlProvider
}