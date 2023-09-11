package com.example.metroyab.data.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("address")
    val address: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("neighbourhood")
    val neighbourhood: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)