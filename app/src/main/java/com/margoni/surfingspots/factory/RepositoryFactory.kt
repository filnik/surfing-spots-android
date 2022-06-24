package com.margoni.surfingspots.factory

import com.margoni.surfingspots.data.CityDataSource
import com.margoni.surfingspots.data.CityDataSourceImpl
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.data.WeatherRepositoryImpl
import com.margoni.surfingspots.data.network.client.HttpClientImpl
import com.margoni.surfingspots.data.network.client.cities.CitiesApiClient
import com.margoni.surfingspots.data.network.utils.MoshiJsonDeserializer

object Factory {

    fun WeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(
            CityDataSource()
        )
    }

    private val jsonDeserializer = MoshiJsonDeserializer()

    private fun CityDataSource(): CityDataSource {
        return CityDataSourceImpl(
            CitiesApiClient(
                HttpClientImpl(),
                jsonDeserializer
            )
        )
    }

}