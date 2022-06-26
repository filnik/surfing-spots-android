package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper
import com.margoni.surfingspots.utils.Constants.THREE_SECONDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val repository: WeatherRepository,
    private val mapper: WeatherListUiStateMapper,
    private val timeToWaitAfterError: Long = THREE_SECONDS
) : ViewModel() {
    private val uiState: MutableStateFlow<WeatherListUiState> =
        MutableStateFlow(WeatherListUiState.Success(emptyList()))

    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        repository.fetch()
            .map { mapper.map(it) }
            .catch { exception ->
                uiState.value = WeatherListUiState.Error(exception)
                resumeAfterError()
            }.collect {
                uiState.value = WeatherListUiState.Success(it)
            }
    }

    private suspend fun resumeAfterError() {
        delay(timeToWaitAfterError)
        fetchData()
    }

    val list: LiveData<WeatherListUiState> =
        this.uiState.asLiveData(viewModelScope.coroutineContext)

}

