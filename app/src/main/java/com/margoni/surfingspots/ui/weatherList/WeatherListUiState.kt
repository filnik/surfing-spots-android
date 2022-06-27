package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

data class WeatherListUiState(
    val list: List<WeatherUiState> = emptyList(),
    val isRetrying: Boolean = false,
    val attempt: Long = 0,
    val error: Error? = null
) {

    fun success(list: List<WeatherUiState>): WeatherListUiState {
        return WeatherListUiState(list = list, isRetrying = false, attempt = 0, error = null)
    }

    fun retrying(attempt: Long): WeatherListUiState {
        return WeatherListUiState(list = list, isRetrying = true, attempt = attempt, error = null)
    }

    fun failure(error: Error): WeatherListUiState {
        return WeatherListUiState(list = list, isRetrying = false, attempt = 0, error = error)
    }

}