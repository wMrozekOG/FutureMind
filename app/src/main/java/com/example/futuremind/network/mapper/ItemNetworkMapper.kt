package com.example.futuremind.network.mapper

import com.example.futuremind.model.Item
import com.example.futuremind.network.ItemNetworkEntity

class ItemNetworkMapper : NetworkEntityMapper<ItemNetworkEntity, Item>() {
    override fun mapFromEntity(entity: ItemNetworkEntity): Item {
        return Item(
            description = entity.description,
            imageUrl = entity.imageUrl,
            modificationDate = entity.modificationDate,
            title = entity.title,
            orderId = entity.orderId
        )
    }

    override fun mapFromEntityList(entities: List<ItemNetworkEntity>): List<Item> {
        return entities
            .filter { performValidation(it) }
            .map { mapFromEntity(it) }
    }

    override fun performValidation(entity: ItemNetworkEntity): Boolean {
        return entity.orderId > -1
    }
}