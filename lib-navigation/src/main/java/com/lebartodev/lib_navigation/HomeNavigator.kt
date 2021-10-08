package com.lebartodev.lib_navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator

interface HomeNavigator {
    fun navigateTo(direction: NavDirections, extras: Navigator.Extras? = null)
    fun navigateTo(navigationFlow: HomeNavigationFlow)
    fun back()
}

fun Fragment.findNavigator() = findParentInstance<HomeNavigator>()


inline fun <reified T> Fragment.findParentInstance(): T {
    return generateSequence(this) { it.parentFragment }.filterIsInstance<T>().firstOrNull()!!
}
