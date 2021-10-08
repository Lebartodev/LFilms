package com.lebartodev.feature_trending.repository

import androidx.annotation.StringRes
import com.lebartodev.feature_trending.R
import com.lebartodev.lib.data.entity.MovieEntity

enum class TrendingCategory(@StringRes val title: Int) {
    POPULAR(R.string.trending_category_popular),
    NOW_PLAYING(R.string.trending_category_now_playing),
    LATEST(R.string.trending_category_latest),
    TOP_RATED(R.string.trending_category_top_rated),
    UPCOMING(R.string.trending_category_upcoming),
}

data class TrendingData(
    val category: TrendingCategory,
    val movies: List<MovieEntity>
)