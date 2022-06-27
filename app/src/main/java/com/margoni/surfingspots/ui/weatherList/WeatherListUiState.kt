package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import kotlin.random.Random.Default.nextInt

sealed class WeatherListUiState {
    data class Success(val list: List<WeatherUiState>) : WeatherListUiState()
    data class Retrying(val attempt: Long) : WeatherListUiState()
    data class Failure(val error: Error) : WeatherListUiState()
}