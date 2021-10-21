package com.example.futuremind.network

import retrofit2.http.GET

    interface RetrofitApi {

        @GET("recruitment-task")
        suspend fun getItems() : List<ItemNetworkEntity>
}