package com.lebartodev.feature_trending.ui

import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebartodev.lib_trending.TrendingData
import com.lebartodev.feature_trending.utils.view.MoviesCarouselView
import com.lebartodev.lib_ui.dp
import java.lang.ref.WeakReference

class TrendingAdapter(private val listener: (Long) -> Unit) :
    RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    private val sharedPool = RecyclerView.RecycledViewPool()
    private val layoutManagerStates = hashMapOf<Long, Parcelable?>()
    private val visibleViews = hashMapOf<Int, WeakReference<ViewHolder>>()

    var data: List<com.lebartodev.lib_trending.TrendingData> = listOf()
        set(value) {
            saveState()
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
        cv.setSharedViewPool(sharedPool)
        cv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            260.dp
        )
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = data[position].category.ordinal.toLong()
        holder.bind(data[position], layoutManagerStates[id], listener)
        visibleViews[holder.hashCode()] = WeakReference(holder)
    }


    override fun getItemCount() = data.size

    override fun onViewRecycled(holder: ViewHolder) {
        val state = holder.v.layoutManager.onSaveInstanceState()
        holder.trendingViewId?.run { layoutManagerStates[this] = state }
        visibleViews.remove(holder.hashCode())
        super.onViewRecycled(holder)
    }

    private fun saveState() {
        for (item in visibleViews.values) {
            item.get()?.let {
                val state = it.v.layoutManager.onSaveInstanceState()
                it.trendingViewId?.run { layoutManagerStates[this] = state }
            }
        }
        visibleViews.clear()
    }

    fun clearState() {
        layoutManagerStates.clear()
        visibleViews.clear()
    }

    class ViewHolder(val v: MoviesCarouselView) : RecyclerView.ViewHolder(v) {
        var trendingViewId: Long? = null
        fun bind(data: com.lebartodev.lib_trending.TrendingData, state: Parcelable? = null, listener: (Long) -> Unit) {
            trendingViewId = data.category.ordinal.toLong()
            v.setTitle(data.category.title)
            v.setMovies(data.movies)
            v.listener = listener
            v.layoutManager.let {
                if (state != null) {
                    it.onRestoreInstanceState(state)
                } else {
                    it.scrollToPosition(0)
                }
            }
        }
    }
}