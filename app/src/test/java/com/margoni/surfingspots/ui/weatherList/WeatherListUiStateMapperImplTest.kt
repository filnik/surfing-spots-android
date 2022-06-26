package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.R
import com.margoni.surfingspots.domain.model.City
import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherListUiStateMapperImplTest {
    private val mapper = WeatherListUiStateMapperImpl()

    @Test
    fun `map sunny weather`() {
        val sunnyWeather = Weather(
            city = City("Sunny City Name", "sunnyCityImageUrl"),
            temperature = 30
        )
        val expected = WeatherUiState(
            "Sunny City Name",
            "Sunny - 30 degrees",
            "sunnyCityImageUrl",
            R.color.sunny_foreground
        )

        val actual = mapper.map(listOf(sunnyWeather)).single()

        assertEquals(expected, actual)
    }

    @Test
    fun `map cloudy weather`() {
        val cloudyWeather = Weather(
            city = City("Cloudy City Name", "cloudyCityImageUrl"),
            temperature = 29
        )
        val expected = WeatherUiState(
            "Cloudy City Name",
            "Cloudy - 29 degrees",
            "cloudyCityImageUrl",
            R.color.cloudy_foreground
        )

        val actual = mapper.map(listOf(cloudyWeather)).single()

        assertEquals(expected, actual)
    }

}