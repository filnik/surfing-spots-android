package com.margoni.surfingspots.data

import com.margoni.surfingspots.data.network.client.cities.CitiesApi
import com.margoni.surfingspots.domain.model.City

class CityDataSourceImpl(
    private val api: CitiesApi,
    private val images: CityImageUrlDataSource
) : CityDataSource {

    override suspend fun list(): List<City> {
        return api.fetch().cities.map { City(it.name, images.imageFor(it.name)) }
    }

}