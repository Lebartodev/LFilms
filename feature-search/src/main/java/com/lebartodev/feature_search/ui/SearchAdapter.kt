package com.lebartodev.feature_search.ui

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib_ui.MovieView

class SearchAdapter(private val listener: (Long) -> Unit) :
    PagingDataAdapter<MovieEntity, SearchAdapter.ViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = MovieView(parent.context)
        cv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }


    class ViewHolder(private val v: MovieView) : RecyclerView.ViewHolder(v) {
        fun bind(data: MovieEntity, listener: (Long) -> Unit) {
            v.setMovie(data)
            v.setOnClickListener { listener(data.id) }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem == newItem
    }
}