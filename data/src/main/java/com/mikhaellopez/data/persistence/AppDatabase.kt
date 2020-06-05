package com.mikhaellopez.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikhaellopez.data.persistence.dao.RepoDao
import com.mikhaellopez.data.persistence.entity.RepoEntity

@Database(entities = [(RepoEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

}
