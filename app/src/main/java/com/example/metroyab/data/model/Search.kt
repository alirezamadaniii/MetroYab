package com.example.metroyab.data.model


import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("count")
    val count: Int,
    @SerializedName("items")
    val items: List<Item>
)