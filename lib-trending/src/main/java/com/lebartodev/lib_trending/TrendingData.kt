package com.lebartodev.lib_trending

import com.lebartodev.lib.data.entity.MovieEntity

enum class TrendingCategory {
    POPULAR, NOW_PLAYING, LATEST, TOP_RATED, UPCOMING,
}

data class TrendingData(
    val category: TrendingCategory,
    val movies: List<MovieEntity>
)