package com.margoni.surfingspots.data

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetch(): Flow<WeathersData>
}
