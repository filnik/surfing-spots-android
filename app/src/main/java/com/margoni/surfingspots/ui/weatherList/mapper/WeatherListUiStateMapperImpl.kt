package com.margoni.surfingspots.ui.weatherList.mapper

import com.margoni.surfingspots.R
import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

class WeatherListUiStateMapperImpl : WeatherListUiStateMapper {

    override fun map(from: List<Weather>): List<WeatherUiState> {
        return from.mapIndexed { index, item ->
            val isSunny = item.temperature >= 30
            WeatherUiState(
                item.city.name,
                "${if (isSunny) "Sunny" else "Cloudy"} - ${item.temperature} degrees",
                item.city.imageUrl,
                if (isSunny) R.color.sunny_foreground else R.color.cloudy_foreground,
                index == from.lastIndex
            )
        }
    }

}