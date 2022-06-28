package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.City
import com.margoni.surfingspots.domain.model.Weather
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val cityDataSource = mockk<CityDataSource>()
    private val localRandomTemperatureGenerator = mockk<RandomTemperatureGenerator>()
    private val remoteRandomTemperatureGenerator = mockk<RemoteRandomTemperatureGenerator>()
    private val randomSequenceGenerator = mockk<RandomSequenceGenerator>()

    @Test
    fun `emit weathers with two sequences of temperature refresh successfully`() = runTest(testDispatcher) {
        val state = WeatherRepositoryImpl.State()
        val repository = initRepository(state)
        val cityList = listOf(
            City("City A", "CityImageUrlA"),
            City("City B", "CityImageUrlB"),
            City("City C", "CityImageUrlC")
        )
        coEvery { cityDataSource.list() } returns cityList
        coEvery { localRandomTemperatureGenerator.generate() } returnsMany listOf(6, 54, 43)
        coEvery { randomSequenceGenerator.generate(3) } returns listOf(1, 2, 0)
        coEvery { randomSequenceGenerator.nextSequence( listOf(1, 2, 0)) } returns listOf(2, 0, 1)
        coEvery { remoteRandomTemperatureGenerator.generate() } returnsMany listOf(34, 12, 46, 54, 60, 57)

        val actual: List<WeathersData> = repository.fetch().take(21).toList()

        val expectedFlows = listOf(
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[1], 54),
                    Weather(cityList[2], 43),
                    Weather(cityList[0], 6)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[2], 43),
                    Weather(cityList[1], 34),
                    Weather(cityList[0], 6)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[1], 34),
                    Weather(cityList[2], 12),
                    Weather(cityList[0], 6)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[0], 46),
                    Weather(cityList[1], 34),
                    Weather(cityList[2], 12)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[2], 54),
                    Weather(cityList[0], 46),
                    Weather(cityList[1], 34)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[0], 60),
                    Weather(cityList[2], 54),
                    Weather(cityList[1], 34)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[0], 60),
                    Weather(cityList[1], 57),
                    Weather(cityList[2], 54)
                )
            ),
            WeathersData.Fetching(false)
        )

        assertThat(actual).containsSequence(expectedFlows)
    }

    @Test
    fun `emit weathers and refreshes from checkpoint saved into state successfully`() = runTest(testDispatcher) {
        val cityList = listOf(
            City("City A", "CityImageUrlA"),
            City("City B", "CityImageUrlB"),
            City("City C", "CityImageUrlC")
        )
        val state = WeatherRepositoryImpl.State(
            weathers = mutableListOf(
                Weather(cityList[0], 6),
                Weather(cityList[1], 34),
                Weather(cityList[2], 43)
            ),
            randomSequence = listOf(1, 2, 0),
            index = 1
        )
        val repository = initRepository(state)

        coEvery { randomSequenceGenerator.nextSequence( listOf(1, 2, 0)) } returns listOf(2, 0, 1)
        coEvery { remoteRandomTemperatureGenerator.generate() } returnsMany listOf(3, 12, 37, 54, 22)

        val actual: List<WeathersData> = repository.fetch().take(15).toList()

        val expectedFlows = listOf(
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[1], 34),
                    Weather(cityList[0], 6),
                    Weather(cityList[2], 3)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[1], 34),
                    Weather(cityList[0], 12),
                    Weather(cityList[2], 3)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[2], 37),
                    Weather(cityList[1], 34),
                    Weather(cityList[0], 12)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[0], 54),
                    Weather(cityList[2], 37),
                    Weather(cityList[1], 34)
                )
            ),
            WeathersData.Fetching(false),
            WeathersData.Fetching(true),
            WeathersData.Data(
                listOf(
                    Weather(cityList[0], 54),
                    Weather(cityList[2], 37),
                    Weather(cityList[1], 22)
                )
            ),
            WeathersData.Fetching(false)
        )

        assertThat(actual).containsSequence(expectedFlows)
    }

    private fun initRepository(state: WeatherRepositoryImpl.State): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(
            cityDataSource,
            localRandomTemperatureGenerator,
            remoteRandomTemperatureGenerator,
            randomSequenceGenerator,
            state,
            testDispatcher
        )
    }
}