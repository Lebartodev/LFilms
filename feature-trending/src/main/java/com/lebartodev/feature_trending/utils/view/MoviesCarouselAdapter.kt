package com.lebartodev.feature_trending.utils.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib_ui.MovieView

class MoviesCarouselAdapter(private val listener: (Long) -> Unit) :
    RecyclerView.Adapter<MoviesCarouselAdapter.ViewHolder>() {
    var data: List<MovieEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = MovieView(parent.context)
        v.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
        data[position], listener
    )

    override fun getItemCount() = data.size

    class ViewHolder(private val view: MovieView) : RecyclerView.ViewHolder(view) {
        fun bind(item: MovieEntity, listener: (Long) -> Unit) {
            view.setMovie(item)
            view.setOnClickListener { listener(item.id) }
        }
    }
}