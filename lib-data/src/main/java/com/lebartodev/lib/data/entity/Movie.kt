package com.lebartodev.lib.data.entity

import androidx.room.Junction
import androidx.room.Relation

class Movie(
    id: Long,
    title: String,
    posterPath: String?,
    overview: String?,
    voteAverage: Double,
    adult: Boolean?,
    budget: Int?,
    voteCount: Int?
) : MovieEntity(id, title, posterPath, overview, voteAverage, adult, budget, voteCount) {
    @Relation(
        parentColumn = "id",
        entity = GenreEntity::class,
        entityColumn = "noteId",
        associateBy = Junction(
            value = MovieGenre::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    var genres: List<GenreEntity> = arrayListOf()
}