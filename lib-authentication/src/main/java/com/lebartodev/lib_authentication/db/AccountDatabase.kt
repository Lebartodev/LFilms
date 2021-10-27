package com.lebartodev.lib_authentication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lebartodev.lib_authentication.db.dao.AccountDao
import com.lebartodev.lib_authentication.db.entity.AccountEntity

@Database(
    entities = [AccountEntity::class],
    version = 1
)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}