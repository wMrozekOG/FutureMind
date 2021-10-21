package com.example.futuremind.database

import androidx.room.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM items order by orderId asc")
    fun get(): List<ItemCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(movies: List<ItemCacheEntity>)
}