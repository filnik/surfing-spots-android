package com.margoni.surfingspots.factory

import com.margoni.surfingspots.data.*
import com.margoni.surfingspots.data.network.client.HttpClientImpl
import com.margoni.surfingspots.data.network.client.cities.CitiesApiClient
import com.margoni.surfingspots.data.network.utils.MoshiJsonDeserializer

object Factory {

    fun WeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(
            CityDataSource(),
            LocalTemperatureGenerator()
        )
    }

    private val jsonDeserializer = MoshiJsonDeserializer()

    private fun CityDataSource(): CityDataSource {
        return CityDataSourceImpl(
            CitiesApiClient(
                HttpClientImpl(),
                jsonDeserializer
            ),
            CityImageUrlDataSourceImpl()
        )
    }

}