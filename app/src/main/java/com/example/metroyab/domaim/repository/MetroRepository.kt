package com.example.metroyab.domaim.repository

import com.example.metroyab.data.model.Search
import com.example.metroyab.data.utlis.Resource

interface MetroRepository {

    suspend fun search(
        term:String,
        lat:Double,
        lng:Double
    ):Resource<Search>
}