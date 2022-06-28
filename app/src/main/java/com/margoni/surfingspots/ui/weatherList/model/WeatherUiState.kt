package com.margoni.surfingspots.ui.weatherList.model

data class WeatherUiState(
    val city: String,
    val description: String,
    val imageUrl: String,
    val foregroundColor: Int,
    val index: Int
)