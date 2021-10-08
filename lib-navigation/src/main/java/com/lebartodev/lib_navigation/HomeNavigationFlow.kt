package com.lebartodev.lib_navigation

sealed class HomeNavigationFlow {
    object Trending : HomeNavigationFlow()
    object Search : HomeNavigationFlow()
}