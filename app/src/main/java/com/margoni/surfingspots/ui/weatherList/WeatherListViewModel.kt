package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll

class WeatherListViewModel(private val repository: WeatherRepository) : ViewModel() {

    val list: LiveData<List<Weather>>
        get() = fetchFrom(repository).asLiveData(viewModelScope.coroutineContext)

    private fun fetchFrom(repository: WeatherRepository): Flow<List<Weather>> {
        return repository.fetch()
            .catch {
                emitAll(fetchFrom(repository))
            }
    }

}

