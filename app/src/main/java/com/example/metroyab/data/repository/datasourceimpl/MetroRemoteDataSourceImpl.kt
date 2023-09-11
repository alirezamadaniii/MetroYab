package com.example.metroyab.data.repository.datasourceimpl

import com.example.metroyab.data.api.ApiService
import com.example.metroyab.data.model.Search
import com.example.metroyab.data.repository.datasource.MetroRemoteDataSource
import retrofit2.Response

class MetroRemoteDataSourceImpl(private val apiService: ApiService) :MetroRemoteDataSource {
    override suspend fun search(term: String, lat: Double, lng: Double): Response<Search> {
        return apiService.search(term, lat, lng)
    }
}