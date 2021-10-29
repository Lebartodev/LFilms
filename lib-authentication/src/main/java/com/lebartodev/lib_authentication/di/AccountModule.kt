package com.lebartodev.lib_authentication.di

import android.app.Application
import androidx.room.Room
import com.lebartodev.lib_authentication.db.AccountDatabase
import com.lebartodev.lib_authentication.db.dao.AccountDao
import com.lebartodev.lib_authentication.network.AccountService
import com.lebartodev.lib_authentication.manager.AccountManager
import com.lebartodev.lib_authentication.manager.AccountManagerImpl
import com.lebartodev.lib_utils.di.scope.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [AccountDatabaseModule::class, AuthenticationRepositoryModule::class, AccountNetworkModule::class])
interface AuthenticationModule

@Module
interface AuthenticationRepositoryModule {
    @AppScope
    @Binds
    fun provideRepository(manager: AccountManagerImpl): AccountManager
}

@Module
class AccountDatabaseModule {
    @AppScope
    @Provides
    fun provideDatabase(application: Application): AccountDatabase =
        Room.databaseBuilder(application, AccountDatabase::class.java, "account_database.db")
            .build()

    @Provides
    fun provideAccountDao(appDatabase: AccountDatabase): AccountDao = appDatabase.accountDao()
}

@Module
class AccountNetworkModule {
    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)
}