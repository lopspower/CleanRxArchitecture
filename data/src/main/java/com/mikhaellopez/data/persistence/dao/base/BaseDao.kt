package com.mikhaellopez.data.persistence.dao.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {

    /**
     * Insert a entity into the table.
     * @param entity
     * @return The row ID of the newly inserted entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T): Long

    /**
     * Delete an entity
     * @return A number of entity deleted. This should always be `1`
     */
    @Delete
    fun delete(entity: T): Int

}
