package com.example.metroyab.data.repository

import com.example.metroyab.data.model.Search
import com.example.metroyab.data.repository.datasource.MetroRemoteDataSource
import com.example.metroyab.data.utlis.Resource
import com.example.metroyab.domaim.repository.MetroRepository
import retrofit2.Response

class MetroRepositoryImpl(
    private val remoteDataSource: MetroRemoteDataSource
) :MetroRepository{
    override suspend fun search(term: String, lat: Double, lng: Double): Resource<Search> {
        return responseToResource(remoteDataSource.search(term,lat, lng))
    }

    private fun responseToResource(response: Response<Search>):Resource<Search>{
        if (response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())

    }

}