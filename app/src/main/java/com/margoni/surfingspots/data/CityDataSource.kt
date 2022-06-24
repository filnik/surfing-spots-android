package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.City

interface CityDataSource {
    suspend fun list(): List<City>
}

