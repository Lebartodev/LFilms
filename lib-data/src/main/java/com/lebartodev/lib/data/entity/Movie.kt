package com.lebartodev.lib.data.entity

import androidx.room.Junction
import androidx.room.Relation

class Movie(
    id: Long,
    title: String,
    posterPath: String?,
    overview: String?,
    voteAverage: Double?,
    adult: Boolean?,
    budget: Int?,
    voteCount: Int?
) : MovieEntity(id, title, posterPath, overview, voteAverage, adult, budget, voteCount) {
    @Relation(
        parentColumn = "id",
        entity = GenreEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = MovieGenre::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    var genres: List<GenreEntity> = arrayListOf()

    @Relation(
        parentColumn = "id",
        entity = CastEntity::class,
        entityColumn = "movieId",
    )
    var cast: List<CastEntity> = arrayListOf()
}