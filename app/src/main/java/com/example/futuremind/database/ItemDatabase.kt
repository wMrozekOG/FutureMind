package com.example.futuremind.database

import androidx.room.*

@Database(entities = [ItemCacheEntity::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao
}