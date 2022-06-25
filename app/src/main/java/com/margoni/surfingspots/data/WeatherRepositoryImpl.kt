package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val THREE_SECONDS = 3000L

class WeatherRepositoryImpl(
    private val cityDataSource: CityDataSource,
    private val localRandomTemperatureGenerator: RandomTemperatureGenerator,
    private val remoteRandomTemperatureGenerator: RemoteRandomTemperatureGenerator,
    private val randomSequenceGenerator: RandomSequenceGenerator,
    private val defaultDispatcher: CoroutineDispatcher = Default,
    private val refreshIntervalMs: Long = THREE_SECONDS
) : WeatherRepository {

    override fun fetch(): Flow<List<Weather>> = flow {
        val cities = cityDataSource.list()

        val weathers =
            cities.map { city -> Weather(city, localRandomTemperatureGenerator.generate()) }
                .toMutableList()
                .also { emit(it.sortedByDescendingTemperature()) }

        startUpdate(weathers)
    }
        .flowOn(defaultDispatcher)

    private suspend fun FlowCollector<List<Weather>>.startUpdate(weathers: MutableList<Weather>) {
        var index = 0
        var randomSequence = randomSequenceGenerator.generate(size = weathers.size)

        while (true) {
            delay(refreshIntervalMs)

            weathers.updateTemperatureOf(
                elementAt = randomSequence[index],
                temperature = remoteRandomTemperatureGenerator.generate()
            ).also { emit(it.sortedByDescendingTemperature()) }

            index++

            if (index == weathers.size) {
                index = 0
                randomSequence = randomSequenceGenerator.nextSequence(from = randomSequence)
            }
        }
    }

    private fun Iterable<Weather>.sortedByDescendingTemperature() =
        this.sortedByDescending { it.temperature }

    private fun MutableList<Weather>.updateTemperatureOf(
        elementAt: Int,
        temperature: Int
    ): MutableList<Weather> {
        this[elementAt] = this[elementAt].copy(temperature = temperature)
        return this
    }

}