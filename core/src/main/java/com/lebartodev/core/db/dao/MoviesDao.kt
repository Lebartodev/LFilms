package com.lebartodev.core.db.dao

import androidx.room.*
import com.lebartodev.lib.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieentity")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertMovieInternal(movieEntity: MovieEntity): Long

    @Update
    suspend fun updateMovieInternal(movieEntity: MovieEntity): Int

    @Transaction
    suspend fun insertMovie(movieEntity: MovieEntity): Long {
        val id: Long
        if (updateMovieInternal(movieEntity) == 0) {
            id = insertMovieInternal(movieEntity)
        } else {
            id = movieEntity.id
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