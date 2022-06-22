package com.margoni.surfingspots

import androidx.lifecycle.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class WeatherListViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun list(): LiveData<List<Weather>> = repository.fetch()
        .asLiveData(viewModelScope.coroutineContext)

}

