package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.Weather
import com.margoni.surfingspots.utils.Constants.THREE_SECONDS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val cityDataSource: CityDataSource,
    private val localRandomTemperatureGenerator: RandomTemperatureGenerator,
    private val remoteRandomTemperatureGenerator: RemoteRandomTemperatureGenerator,
    private val randomSequenceGenerator: RandomSequenceGenerator,
    private val state: State = State(),
    private val defaultDispatcher: CoroutineDispatcher = Default,
    private val refreshIntervalMs: Long = THREE_SECONDS
) : WeatherRepository {

    override fun fetch(): Flow<WeathersData> {
        return flow {
            init()
            startUpdate()
        }.flowOn(defaultDispatcher)
    }

    private suspend fun FlowCollector<WeathersData>.init() = with(state) {
        if (weathers.isEmpty()) {
            emitStartFetchingData()
            cityDataSource.list()
                .map { city -> Weather(city, localRandomTemperatureGenerator.generate()) }
                .also { weathers.addAll(it) }
            index = 0
            randomSequence = randomSequenceGenerator.generate(state.weathers.size)
            emitData()
            emitStopFetchingData()
            delay(refreshIntervalMs)
        }
    }

    private suspend fun FlowCollector<WeathersData>.startUpdate() = with(state) {
        while (true) {
            emitStartFetchingData()

            weathers.updateTemperatureOf(
                elementAt = randomSequence[index],
                temperature = remoteRandomTemperatureGenerator.generate()
            )

            emitData()
            emitStopFetchingData()

            index++

            if (index == randomSequence.size) {
                initNextSequence()
            }

            delay(refreshIntervalMs)
        }
    }

    private fun initNextSequence() {
        state.index = 0
        state.randomSequence = randomSequenceGenerator.nextSequence(from = state.randomSequence)
    }

    private suspend fun FlowCollector<WeathersData>.emitData() {
        emit(WeathersData.Data(state.weathers.sortedByDescendingTemperature()))
    }

    private suspend fun FlowCollector<WeathersData>.emitStartFetchingData() {
        emit(WeathersData.Fetching(true))
    }

    private suspend fun FlowCollector<WeathersData>.emitStopFetchingData() {
        emit(WeathersData.Fetching(false))
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

    data class State(
        val weathers: MutableList<Weather> = mutableListOf(),
        var index: Int = 0,
        var randomSequence: List<Int> = emptyList()
    )

}

sealed class WeathersData {
    data class Fetching(val status: Boolean): WeathersData()
    data class Data(val list: List<Weather>): WeathersData()
}