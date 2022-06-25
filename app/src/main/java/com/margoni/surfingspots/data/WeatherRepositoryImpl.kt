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
    private val defaultDispatcher: CoroutineDispatcher = Default,
    private val refreshIntervalMs: Long = THREE_SECONDS
) : WeatherRepository {

    override fun fetch(): Flow<List<Weather>> = flow {
        val cities = cityDataSource.list()

        val weathers = cities.map { city -> Weather(city, localRandomTemperatureGenerator.generate()) }
            .sortedByDescending { it.temperature }
            .toMutableList()
            .also { emit(it) }

        refreshRandomlyOneAtATime(weathers)
    }.flowOn(defaultDispatcher)

    private suspend fun FlowCollector<List<Weather>>.refreshRandomlyOneAtATime(
        weathers: MutableList<Weather>
    ) {
        var index = 0
        while (true) {
            weathers.updateTemperatureOf(index, remoteRandomTemperatureGenerator.generate())
                .sortedByDescending { it.temperature }
                .also { emit(it) }

            index++

            if (index == weathers.size) {
                weathers.shuffle()
                index = 0
            }

            delay(refreshIntervalMs)
        }
    }

    private fun MutableList<Weather>.updateTemperatureOf(
        index: Int,
        temperature: Int
    ): MutableList<Weather> {
        this[index] = this[index].copy(temperature = temperature)
        return this
    }

}