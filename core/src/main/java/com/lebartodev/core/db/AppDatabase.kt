package com.lebartodev.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lebartodev.core.db.dao.CreditsDao
import com.lebartodev.core.db.dao.GenresDao
import com.lebartodev.core.db.dao.MoviesDao
import com.lebartodev.lib.data.entity.CastEntity
import com.lebartodev.lib.data.entity.GenreEntity
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib.data.entity.MovieGenre

@Database(
    entities = [GenreEntity::class, MovieEntity::class, MovieGenre::class, CastEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genresDao(): GenresDao
    abstract fun creditsDao(): CreditsDao
    abstract fun moviesDao(): MoviesDao
}