package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.margoni.surfingspots.data.WeatherRepository

class WeatherListViewModelFactory(private val repository: WeatherRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherListViewModel(repository, 3000) as T
    }

}