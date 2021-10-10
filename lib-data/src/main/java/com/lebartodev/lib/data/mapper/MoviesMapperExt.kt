package com.lebartodev.lib.data.mapper

import com.lebartodev.lib.data.entity.GenreEntity
import com.lebartodev.lib.data.entity.Movie
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.network.GenreResponse
import com.lebartodev.lib.data.network.GenresResponse
import com.lebartodev.lib.data.network.MovieResponse
import com.lebartodev.lib.data.network.MoviesResponse

fun MovieResponse.toEntity() =
    Movie(id, title, poster_path, overview, vote_average, adult, budget, vote_count)
        .also {
            it.genres = this.genres?.map { it.toGenreEntity() } ?: emptyList()
        }

fun MoviesResponse.toMovies(): List<MovieEntity> {
    return this.results?.map { it.toEntity() } ?: emptyList()
}


fun GenreResponse.toGenreEntity() = GenreEntity(id, name)

fun GenresResponse.toGenreEntities() = genres.map { it.toGenreEntity() }
