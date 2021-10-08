package com.lebartodev.feature_search.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lebartodev.lib.data.entity.MovieEntity

class SearchPagingDataSource(
    private val loader: suspend (Int) -> PageData,
) : PagingSource<Int, MovieEntity>() {

    data class PageData(
        val maxPage: Int,
        val data: List<MovieEntity>
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val pageNumber = params.key ?: 1
        try {
            val response = loader(pageNumber)
            val data = response.data

            val nextPageNumber = (pageNumber + 1).takeIf { it <= response.maxPage }
            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition
    }
}