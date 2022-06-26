package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.R
import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

class WeatherListUiStateMapperImpl : WeatherListUiStateMapper {

    override fun map(from: List<Weather>): List<WeatherUiState> {
        return from.map {
            val isSunny = it.temperature >= 30
            WeatherUiState(
                it.city.name,
                "${if (isSunny) "Sunny" else "Cloudy"} - ${it.temperature} degrees",
                it.city.imageUrl,
                if (isSunny) R.color.sunny_foreground else R.color.cloudy_foreground
            )
        }
    }

}