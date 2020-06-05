package com.mikhaellopez.data.persistence

import android.content.Context
import androidx.room.Room

object DatabaseFactoryTest {

    fun getDatabase(context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

}