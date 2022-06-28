package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.R
import com.margoni.surfingspots.domain.model.City
import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapperImpl
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class WeatherListUiStateMapperImplTest {
    private val mapper = WeatherListUiStateMapperImpl()

    @Test
    fun `map sunny and cloudy weathers`() {
        val sunnyWeather = Weather(
            city = City("Sunny City Name", "sunnyCityImageUrl"),
            temperature = 30
        )
        val cloudyWeather = Weather(
            city = City("Cloudy City Name", "cloudyCityImageUrl"),
            temperature = 29
        )

        val actual = mapper.map(listOf(sunnyWeather, cloudyWeather))

        val expected = listOf(
            WeatherUiState(
                "Sunny City Name",
                "Sunny - 30 degrees",
                "sunnyCityImageUrl",
                R.color.sunny_foreground,
                false
            ),
            WeatherUiState(
                "Cloudy City Name",
                "Cloudy - 29 degrees",
                "cloudyCityImageUrl",
                R.color.cloudy_foreground,
                true
            )
        )
        assertThat(actual).isEqualTo(expected)
    }

}