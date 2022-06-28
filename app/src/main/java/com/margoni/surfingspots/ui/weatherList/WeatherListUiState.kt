package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

data class WeatherListUiState(
    val list: List<WeatherUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isRetrying: Boolean = false,
    val attempt: Long = 0,
    val error: Error? = null
) {

    fun loading(isLoading: Boolean): WeatherListUiState {
        return WeatherListUiState(
            list = list,
            isLoading = isLoading,
            isRetrying = false,
            attempt = 0,
            error = null
        )
    }

    fun success(list: List<WeatherUiState>): WeatherListUiState {
        return WeatherListUiState(
            list = list,
            isLoading = isLoading,
            isRetrying = false,
            attempt = 0,
            error = null
        )
    }

    fun retrying(attempt: Long): WeatherListUiState {
        return WeatherListUiState(
            list = list,
            isLoading = true,
            isRetrying = true,
            attempt = attempt,
            error = null
        )
    }

    fun failure(error: Error): WeatherListUiState {
        return WeatherListUiState(
            list = list,
            isLoading = false,
            isRetrying = false,
            attempt = 0,
            error = error
        )
    }

}