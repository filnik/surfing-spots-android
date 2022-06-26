package com.margoni.surfingspots.ui.weatherList.mapper

import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

interface WeatherListUiStateMapper {
    fun map(from: List<Weather>): List<WeatherUiState>
}