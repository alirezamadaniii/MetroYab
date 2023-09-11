package com.example.metroyab.domaim.usecase

import com.example.metroyab.data.model.Search
import com.example.metroyab.data.utlis.Resource
import com.example.metroyab.domaim.repository.MetroRepository

class SearchUseCase(private val repository: MetroRepository) {
    suspend fun execute(
        term:String,
        lat:Double,
        lng:Double
    ):Resource<Search>{
        return repository.search(term, lat, lng)
    }
}