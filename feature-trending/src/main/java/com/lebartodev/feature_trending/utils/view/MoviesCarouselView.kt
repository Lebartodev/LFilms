package com.lebartodev.feature_trending.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lebartodev.feature_trending.databinding.ViewCarouselBinding
import com.lebartodev.lib.data.entity.MovieEntity

class MoviesCarouselView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewCarouselBinding.inflate(LayoutInflater.from(context), this)
    private val adapter =
        MoviesCarouselAdapter { movieId -> listener(movieId) }
    var listener: (Long) -> Unit? = {}

    init {
        binding.carouselList.adapter = adapter
        binding.carouselList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    fun setTitle(text: String) {
        binding.carouselTitle.text = text
    }

    fun setTitle(@StringRes title: Int) {
        binding.carouselTitle.setText(title)
    }

    fun setMovies(data: List<MovieEntity>) {
        adapter.data = data
    }
}