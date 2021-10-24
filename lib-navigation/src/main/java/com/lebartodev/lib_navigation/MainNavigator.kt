package com.lebartodev.lib_navigation

import androidx.fragment.app.Fragment

interface MainNavigator {
    fun navigateTo(fragment: Fragment)
    fun back()
}

val Fragment.navigator: MainNavigator
    get() = findParentInstance()


inline fun <reified T> Fragment.findParentInstance(): T {
    return generateSequence(this) { it.parentFragment }.filterIsInstance<T>().firstOrNull()!!
}
