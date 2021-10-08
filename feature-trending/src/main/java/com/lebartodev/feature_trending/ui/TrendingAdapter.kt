package com.lebartodev.feature_trending.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebartodev.feature_trending.repository.TrendingData
import com.lebartodev.feature_trending.utils.view.MoviesCarouselView
import com.lebartodev.lib_ui.dp

class TrendingAdapter(private val listener: (Long) -> Unit) :
    RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    var data: List<TrendingData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].category.ordinal.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = MoviesCarouselView(parent.context)
        cv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            260.dp
        )
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], listener)

    override fun getItemCount() = data.size

    class ViewHolder(private val v: MoviesCarouselView) : RecyclerView.ViewHolder(v) {
        fun bind(data: TrendingData, listener: (Long) -> Unit) {
            v.setTitle(data.category.title)
            v.setMovies(data.movies)
            v.listener = listener
        }
    }
}