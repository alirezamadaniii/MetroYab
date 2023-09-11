package com.example.metroyab.data.repository.datasource

import com.example.metroyab.data.model.Search
import retrofit2.Response

interface MetroRemoteDataSource {
    suspend fun search(
        term:String,
        lat:Double,
        lng:Double
    ):Response<Search>
}