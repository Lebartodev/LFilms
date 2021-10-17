package com.lebartodev.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.lebartodev.lib.data.entity.CastEntity


@Dao
interface CreditsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCast(cast: List<CastEntity>): List<Long>

    @Update
    suspend fun updateCast(cast: List<CastEntity>)

    @Transaction
    suspend fun upsertCast(cast: List<CastEntity>) {
        val insertResult: List<Long> = insertCast(cast)
        val updateList: MutableList<CastEntity> = ArrayList()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(cast[i])
            }
        }

        if (updateList.isNotEmpty()) {
            updateCast(updateList)
        }
    }
}