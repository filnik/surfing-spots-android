package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.domain.model.Weather

sealed class WeatherListUiState {
    data class Success(val list: List<Weather>) : WeatherListUiState()
    data class Error(val exception: Throwable) : WeatherListUiState()
}