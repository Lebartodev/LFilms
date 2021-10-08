package com.lebartodev.core.db.dao

import androidx.room.*
import com.lebartodev.lib.data.entity.GenreEntity
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

    @Transaction
    suspend fun upsertGenres(genres: List<GenreEntity>) {
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
    }
}