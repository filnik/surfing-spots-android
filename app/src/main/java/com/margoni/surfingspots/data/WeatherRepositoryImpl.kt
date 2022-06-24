package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.City
import com.margoni.surfingspots.domain.model.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

private const val THREE_SECONDS = 3000L

class WeatherRepositoryImpl(
    private val defaultDispatcher: CoroutineDispatcher = Default,
    private val refreshIntervalMs: Long = THREE_SECONDS
) : WeatherRepository {

    private val cityList = listOf(
        City(
            "Cuba",
            "https://www.info-turismo.it/wp-content/uploads/2021/01/shutterstock_1020405811.jpg"
        ),
        City(
            "Los Angeles",
            "https://www.esl.it/sites/default/files/styles/image_gallery/public/city/esl-languages-usa-los-angeles-hero.jpg?itok=GNFHWcI7"
        ),
        City(
            "Miami",
            "https://content.r9cdn.net/rimg/dimg/17/74/0ca6e469-city-30651-1632b88f203.jpg?width=1750&height=1000&xhint=2635&yhint=1507&crop=true"
        ),
        City(
            "Porto",
            "https://d2bgjx2gb489de.cloudfront.net/gbb-blogs/wp-content/uploads/2020/02/09192305/La-vista-panoramica-sul-Centro-storico-di-Porto.jpg"
        ),
        City("Ortona", "https://turismo.abruzzo.it/wp-content/uploads/sites/118/ortona-hd.jpg"),
        City("Riccione", "https://www.dovedormire.info/wp-content/uploads/sites/119/riccione.jpg"),
        City("Midgar", "https://www.everyeye.it/public/immagini/11042020/midgar.jpg"),
    )

    override fun fetch(): Flow<List<Weather>> = flow {
        val weathers = cityList.map { city -> Weather(city, randomTemperature()) }
            .sortedByDescending { it.temperature }
            .toMutableList()
            .also { emit(it) }

        updateRandomly(weathers)
    }.flowOn(defaultDispatcher)

    private suspend fun FlowCollector<List<Weather>>.updateRandomly(
        weathers: MutableList<Weather>
    ) {
        var index = 0
        while (true) {
            weathers.updateTemperatureOf(index, randomTemperature())
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

    private fun randomTemperature() = Random.nextInt(0, 60)

}