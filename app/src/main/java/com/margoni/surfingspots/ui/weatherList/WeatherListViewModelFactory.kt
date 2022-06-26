package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper

class WeatherListViewModelFactory(
    private val repository: WeatherRepository,
    private val mapper: WeatherListUiStateMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherListViewModel(repository, mapper) as T
    }

}