package com.margoni.surfingspots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherListViewModelFactory(private val repository: WeatherRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherListViewModel(repository) as T
    }

}