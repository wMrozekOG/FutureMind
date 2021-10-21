package com.example.futuremind.repository

import com.example.futuremind.database.DatabaseAccess
import com.example.futuremind.database.fromItemList
import com.example.futuremind.database.toItemList
import com.example.futuremind.view.error.ErrorState
import com.example.futuremind.extensions.isNetworkException
import com.example.futuremind.model.Item
import com.example.futuremind.network.ItemApi
import com.example.futuremind.network.mapper.ItemNetworkMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ItemRepository {
    fun getItems(): Flow<RequestState<List<Item>>>
}

class ItemRepositoryImpl constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: ItemApi,
    private val database: DatabaseAccess,
    private val networkMapper: ItemNetworkMapper
) : ItemRepository {

    override fun getItems(): Flow<RequestState<List<Item>>> = flow {
        // Get cached data and emit
        val cachedItems = database.get().toItemList()
        emit(RequestState.Loading(cachedItems))

        // Validate and map fetched data
        val items = networkMapper.mapFromEntityList(api.getItems())

        // Map to cache entities and save to database
        database.put(items.fromItemList())

        // Emit latest data from database
        emit(RequestState.Success(database.get().toItemList()))
    }.catch {
        emit(
            RequestState.Error(
                if (it.isNetworkException()) {
                    ErrorState.NETWORK_ERROR
                } else {
                    ErrorState.DATA_ERROR
                }
            )
        )
    }.flowOn(ioDispatcher)
}