package com.example.futuremind.network

import com.google.gson.annotations.SerializedName

data class ItemNetworkEntity (
	@SerializedName("description") val description : String,
	@SerializedName("image_url") val imageUrl : String,
	@SerializedName("modificationDate") val modificationDate : String,
	@SerializedName("orderId") val orderId : Int,
	@SerializedName("title") val title : String
)