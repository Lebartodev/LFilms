package com.lebartodev.lfilms

import android.app.Application
import com.lebartodev.core.di.CoreComponent
import com.lebartodev.core.di.CoreComponentProvider
import com.lebartodev.core.di.DaggerCoreComponent
import kotlinx.coroutines.cancel

class LFilmsApplication : Application(), CoreComponentProvider {
    override lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = initCore()
        coreComponent.accountRepository()
    }

    override fun onTerminate() {
        super.onTerminate()
        coreComponent.coroutineScope().cancel()
    }

    private fun initCore(): CoreComponent {
        return DaggerCoreComponent.builder()
            .application(this)
            .build().also { it.inject(this) }
    }
}