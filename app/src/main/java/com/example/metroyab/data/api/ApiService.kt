package com.example.metroyab.data.api

import com.example.metroyab.data.model.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Api-Key: service.97c54ef8e7d649b6b19eba02e9612306")
    @GET("search")
    suspend fun search(
        @Query("term") term:String,
        @Query("lat") lat:Double,
        @Query("lng") lng:Double
    ):Response<Search>

}