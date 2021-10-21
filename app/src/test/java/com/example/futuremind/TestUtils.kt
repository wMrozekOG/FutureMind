package com.example.futuremind

import com.example.futuremind.database.ItemCacheEntity
import com.example.futuremind.model.Item
import com.example.futuremind.network.ItemNetworkEntity

fun createItem() = Item(
    title = "Item title",
    description = "Item description",
    orderId = 1,
    imageUrl = "some_url",
    modificationDate = "2019-02-22"
)

fun createNetworkItem() = ItemNetworkEntity(
    title = "Item title",
    description = "Item description",
    orderId = 1,
    imageUrl = "some_url",
    modificationDate = "2019-02-22"
)

fun createDatabaseItem() = ItemCacheEntity(
    title = "Item title",
    description = "Item description",
    orderId = 1,
    imageUrl = "some_url",
    modificationDate = "2019-02-22"
)