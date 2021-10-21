package com.example.futuremind.database

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.futuremind.App
import com.example.futuremind.createDatabaseItem
import com.example.futuremind.createItem
import com.example.futuremind.model.Item
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseTest {
    private lateinit var itemDao: ItemDao
    private lateinit var db: ItemDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = db.itemDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `get sorted items`() {
        val item = createDatabaseItem()
        val items = listOf(item.copy(orderId = 3), item, item.copy(orderId = 5))

        itemDao.put(items)

        assertEquals(items.sortedBy { it.orderId }, itemDao.get())
    }
}
