package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

sealed class WeatherListUiState {
    data class Success(val list: List<WeatherUiState>) : WeatherListUiState()
    data class Error(val exception: Throwable) : WeatherListUiState()
}