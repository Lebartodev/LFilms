package com.lebartodev.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lebartodev.lib.data.entity.CastEntity
import com.lebartodev.lib.data.entity.Movie
import com.lebartodev.lib.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieentity")
    fun getAll(): Flow<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movieentity where id = :id")
    suspend fun getById(id: Long): Movie

    @Insert
    suspend fun insertMovieInternal(movieEntity: MovieEntity): Long

    @Update
    suspend fun updateMovieInternal(movieEntity: MovieEntity): Int

    @Transaction
    suspend fun insertMovie(movie: Movie): Long {
        val id: Long
        if (updateMovieInternal(movie) == 0) {
            id = insertMovieInternal(movie)
        } else {
            id = movie.id
        }
        return id
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movieEntities: List<MovieEntity>): List<Long>

    @Update
    suspend fun updateMovies(movieEntities: List<MovieEntity>)

    @Transaction
    suspend fun upsertMovies(movieEntities: List<MovieEntity>) {
        val insertResult: List<Long> = insertMovies(movieEntities)
        val updateList: MutableList<MovieEntity> = ArrayList()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(movieEntities[i])
            }
        }

        if (updateList.isNotEmpty()) {
            updateMovies(updateList)
        }
    }
}