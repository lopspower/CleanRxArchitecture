package com.mikhaellopez.data.persistence

import android.content.Context
import androidx.room.Room
import com.mikhaellopez.data.R

object DatabaseFactory {

    fun getDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.DATABASE_NAME)
        ).build()

}
