package com.example.futuremind.network

interface ItemApi {
    suspend fun getItems(): List<ItemNetworkEntity>
}

class ItemApiImpl(
    private val retrofitApi: RetrofitApi
) : ItemApi {

    override suspend fun getItems(): List<ItemNetworkEntity> = retrofitApi.getItems()
}