package com.example.metroyab.data.model


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)