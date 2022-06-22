package com.margoni.surfingspots

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetch(): Flow<List<Weather>>
}
