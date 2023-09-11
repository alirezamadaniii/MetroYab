package com.example.metroyab.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metroyab.data.model.Search
import com.example.metroyab.data.utlis.Resource
import com.example.metroyab.domaim.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val searchUseCase: SearchUseCase):ViewModel() {
    private val search = MutableStateFlow<Resource<Search>?>(null)
    val searchResult
        get() = search.asStateFlow()
    fun searchData(term:String,lat:Double,lng:Double) = viewModelScope.launch(Dispatchers.IO) {
        search.emit(Resource.Loading())
        try {
                search.emit(searchUseCase.execute(term, lat, lng))

        }catch (e:Exception){
            search.emit(Resource.Error("مشکل در ارتباط با سرور"))
        }

    }
}