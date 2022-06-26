package com.margoni.surfingspots.ui.weatherList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.margoni.surfingspots.TestUtils.valueObservedFrom
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.data.network.NetworkException
import com.margoni.surfingspots.domain.model.City
import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper
import com.margoni.surfingspots.ui.weatherList.model.GenericError
import com.margoni.surfingspots.ui.weatherList.model.NetworkError
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class WeatherListViewModelTest {
    private lateinit var repository: WeatherRepository
    private lateinit var mapper: WeatherListUiStateMapper
    private lateinit var viewModel: WeatherListViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()
        mapper = mockk()
        viewModel = WeatherListViewModel(repository, mapper, 1)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch weather list successfully`() = runTest {
        val weatherList = listOf(Weather(City("cityName", "imageUrl"), 12))
        coEvery { repository.fetch() } returns flow { emit(weatherList) }
        val expectedList = listOf(WeatherUiState("cityName", "description", "imageUrl", 1))
        coEvery { mapper.map(weatherList) } returns expectedList

        val actual = valueObservedFrom(liveData = viewModel.list)

        assertEquals(WeatherListUiState.Success(expectedList), actual)
    }

    @Test
    fun `catch unknown exception`() = runTest {
        val exception = RuntimeException("Text Exception")
        coEvery { repository.fetch() } returns flow { throw exception }

        val actual = valueObservedFrom(liveData = viewModel.list)

        assertEquals(WeatherListUiState.Failure(GenericError()), actual)
    }

    @Test
    fun `retry on network exception`() = runTest {
        val exception = NetworkException()
        coEvery { repository.fetch() } returns flow { throw exception }

        val actual = valueObservedFrom(liveData = viewModel.list)

        assertEquals(
            WeatherListUiState.Retrying("A network error occurred, retrying to connect attempt 1..."),
            actual
        )
    }

    @Test
    fun `handle network error if exceeded attempts`() = runTest {
        initViewModelWithZeroRetryAttempts()
        val exception = NetworkException()
        coEvery { repository.fetch() } returns flow { throw exception } andThen flow { throw exception }

        val actual = valueObservedFrom(liveData = viewModel.list)

        assertEquals(WeatherListUiState.Failure(NetworkError()), actual)
    }

    private fun initViewModelWithZeroRetryAttempts() {
        viewModel = WeatherListViewModel(repository, mapper, 0)
    }

}

