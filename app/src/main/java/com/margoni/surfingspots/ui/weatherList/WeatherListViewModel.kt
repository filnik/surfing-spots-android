package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.*
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.domain.model.Weather

class WeatherListViewModel(private val repository: WeatherRepository) : ViewModel() {

    fun list(): LiveData<List<Weather>> = repository.fetch()
        .asLiveData(viewModelScope.coroutineContext)

}

