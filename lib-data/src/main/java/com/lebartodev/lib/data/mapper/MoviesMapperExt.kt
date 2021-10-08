package com.lebartodev.lib.data.mapper

import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.network.MovieResponse
import com.lebartodev.lib.data.network.MoviesResponse

fun MovieResponse.toEntity() =
    MovieEntity(id, title, poster_path, overview, vote_average, adult, budget, vote_count)

fun MoviesResponse.toMovies(): List<MovieEntity> {
    return this.results?.map { it.toEntity() } ?: emptyList()
}