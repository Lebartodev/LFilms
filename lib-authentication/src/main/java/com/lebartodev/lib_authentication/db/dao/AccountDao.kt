package com.lebartodev.lib_authentication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lebartodev.lib_authentication.db.entity.AccountEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDao {
    @Query("SELECT * FROM accountentity limit 1")
    fun account(): Flow<AccountEntity>

    @Query("SELECT * FROM accountentity limit 1")
    suspend fun getAccount(): AccountEntity?

    @Insert
    suspend fun insertAccountInternal(account: AccountEntity): Long

    @Update
    suspend fun updateAccountInternal(account: AccountEntity): Int

    @Transaction
    suspend fun upsertAccount(genre: AccountEntity): Long {
        val id: Long
        if (updateAccountInternal(genre) == 0) {
            id = insertAccountInternal(genre)
        } else {
            id = genre.id
        }
        return id
    }
}