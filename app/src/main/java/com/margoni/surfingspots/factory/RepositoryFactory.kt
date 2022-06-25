package com.margoni.surfingspots.factory

import com.margoni.surfingspots.data.*
import com.margoni.surfingspots.data.network.client.HttpClientImpl
import com.margoni.surfingspots.data.network.client.cities.CitiesApi
import com.margoni.surfingspots.data.network.client.cities.CitiesApiClient
import com.margoni.surfingspots.data.network.client.randomNumber.RandomNumberApi
import com.margoni.surfingspots.data.network.client.randomNumber.RandomNumberApiClient
import com.margoni.surfingspots.data.network.utils.MoshiJsonDeserializer

object Factory {

    fun WeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(
            cityDataSource = cityDataSource(),
            localRandomTemperatureGenerator = LocalRandomTemperatureGenerator(),
            remoteRandomTemperatureGenerator = RemoteRandomTemperatureGenerator(randomNumberApi()),
            randomSequenceGenerator = RandomSequenceGeneratorImpl()
        )
    }

    private fun cityDataSource(): CityDataSource {
        return CityDataSourceImpl(
            citiesApiClient(),
            CityImageUrlDataSourceImpl()
        )
    }

    private fun citiesApiClient(): CitiesApi {
        return CitiesApiClient(
            HttpClientImpl(),
            MoshiJsonDeserializer()
        )
    }

    private fun randomNumberApi(): RandomNumberApi {
        return RandomNumberApiClient(
            HttpClientImpl()
        )
    }

}