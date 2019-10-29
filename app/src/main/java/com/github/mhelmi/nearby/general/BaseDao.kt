package com.github.mhelmi.nearby.general

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param item the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    /**
     * Insert an list of objects in the database.
     *
     * @param itemList the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemList: List<T>)

    /**
     * Update an object from the database.
     *
     * @param item the object to be updated
     */
    @Update
    fun update(item: T)

    /**
     * Delete an object from the database
     *
     * @param item the object to be deleted
     */
    @Delete
    fun delete(item: T)
}