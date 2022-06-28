package com.margoni.surfingspots.ui.weatherList

import com.margoni.surfingspots.ui.weatherList.model.GenericError
import com.margoni.surfingspots.ui.weatherList.model.NetworkError
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WeatherListUiStateTest {
    private val anyList = listOf(WeatherUiState("city", "description", "imageUrl", 123, true))

    @Test
    fun loading() {
        val initialState = WeatherListUiState(anyList, false, false, 0, null)

        val actualState = initialState.loading()

        assertThat(actualState).isEqualTo(initialState.copy(isLoading = true))
    }

    @Test
    fun success() {
        val initialState = WeatherListUiState(emptyList(), true, true, 1, GenericError())

        val actualState = initialState.success(anyList)

        assertThat(actualState).isEqualTo(WeatherListUiState(anyList, false, false, 0, null))
    }

    @Test
    fun failure() {
        val initialState = WeatherListUiState(anyList, true, true, 1, NetworkError())

        val actualState = initialState.failure(GenericError())

        assertThat(actualState).isEqualTo(WeatherListUiState(anyList, false, false, 0, GenericError()))
    }

    @Test
    fun retrying() {
        val initialState = WeatherListUiState(anyList, true, false, 0, GenericError())

        val actualState = initialState.retrying(50)

        assertThat(actualState).isEqualTo(WeatherListUiState(anyList, false, true, 50, null))
    }

}