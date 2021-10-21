package com.example.futuremind.database

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.futuremind.model.Item

@Keep
@Entity(tableName = "items")
data class ItemCacheEntity(
    @PrimaryKey val orderId : Int,
    val description : String,
    val imageUrl : String,
    val modificationDate : String,
    val title : String
)

fun List<ItemCacheEntity>.toItemList(): List<Item> {
    return this.map {
        Item(
            description = it.description,
            orderId = it.orderId,
            imageUrl = it.imageUrl,
            modificationDate = it.modificationDate,
            title = it.title
        )
    }
}

fun List<Item>.fromItemList(): List<ItemCacheEntity> {
    return this.map {
        ItemCacheEntity(
            description = it.description,
            orderId = it.orderId,
            imageUrl = it.imageUrl,
            modificationDate = it.modificationDate,
            title = it.title
        )
    }
}