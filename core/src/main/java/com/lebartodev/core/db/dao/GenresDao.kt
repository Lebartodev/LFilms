package com.lebartodev.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lebartodev.lib.data.entity.GenreEntity
import com.lebartodev.lib.data.entity.MovieGenre
import kotlinx.coroutines.flow.Flow


@Dao
interface GenresDao {
    @Query("SELECT * FROM genreentity")
    fun getAll(): Flow<List<GenreEntity>>

    @Insert
    suspend fun insertGenreInternal(genre: GenreEntity): Long

    @Update
    suspend fun updateGenreInternal(genre: GenreEntity): Int

    @Transaction
    suspend fun insertGenre(genre: GenreEntity): Long {
        val id: Long
        if (updateGenreInternal(genre) == 0) {
            id = insertGenreInternal(genre)
        } else {
            id = genre.id
        }
        return id
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<GenreEntity>): List<Long>

    @Update
    suspend fun updateGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenres(movieGenres: List<MovieGenre>): List<Long>

    @Update
    suspend fun updateMovieGenres(movieGenres: List<MovieGenre>)

    @Transaction
    suspend fun upsertGenres(movieId: Long, genres: List<GenreEntity>) {
        val insertResult: List<Long> = insertGenres(genres)
        val updateList: MutableList<GenreEntity> = ArrayList()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(genres[i])
            }
        }

        if (updateList.isNotEmpty()) {
            updateGenres(updateList)
        }
        upsertMovieGenres(genres.map { MovieGenre(movieId, it.id) })
    }

    @Transaction
    suspend fun upsertMovieGenres(movieGenres: List<MovieGenre>) {
        val insertResult: List<Long> = insertMovieGenres(movieGenres)
        val updateList: MutableList<MovieGenre> = ArrayList()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(movieGenres[i])
            }
        }

        if (updateList.isNotEmpty()) {
            updateMovieGenres(updateList)
        }
    }
}