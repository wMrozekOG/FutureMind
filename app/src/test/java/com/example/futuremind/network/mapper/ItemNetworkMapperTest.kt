package com.example.futuremind.network.mapper

import com.example.futuremind.createItem
import com.example.futuremind.createNetworkItem
import org.junit.Assert
import org.junit.Test

class ItemNetworkMapperTest {

    @Test
    fun `map list with valid items`() {
        val item = createItem()
        val networkItem = createNetworkItem()
        val networkItems = listOf(networkItem, networkItem.copy(orderId = 2), networkItem.copy(orderId = 5))
        val expected = listOf(item, item.copy(orderId = 2), item.copy(orderId = 5))

        Assert.assertEquals(expected, ItemNetworkMapper().mapFromEntityList(networkItems))
    }

    @Test
    fun `map list with invalid items`() {
        val item = createItem()
        val networkItem = createNetworkItem()
        val networkItems = listOf(networkItem.copy(orderId = 0), networkItem.copy(orderId = -1), networkItem.copy(orderId = 2))
        val expected = listOf(item.copy(orderId = 0), item.copy(orderId = 2))

        Assert.assertEquals(expected, ItemNetworkMapper().mapFromEntityList(networkItems))
    }
}