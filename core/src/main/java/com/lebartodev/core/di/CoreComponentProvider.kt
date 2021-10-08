package com.lebartodev.core.di

import android.content.Context

interface CoreComponentProvider {
    val coreComponent: CoreComponent
}

fun Context.coreComponent(): CoreComponent =
    (applicationContext as CoreComponentProvider).coreComponent