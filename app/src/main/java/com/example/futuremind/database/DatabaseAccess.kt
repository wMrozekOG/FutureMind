package com.example.futuremind.database

interface DatabaseAccess {
    fun get(): List<ItemCacheEntity>

    fun put(items: List<ItemCacheEntity>)
}

class DatabaseAccessImpl(
    private val db: ItemDatabase
): DatabaseAccess {

    override fun get(): List<ItemCacheEntity> {
        return db.itemDao().get()
    }

    override fun put(items: List<ItemCacheEntity>) {
        db.itemDao().put(items)
    }
}