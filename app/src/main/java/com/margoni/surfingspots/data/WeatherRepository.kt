package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetch(): Flow<List<Weather>>
}
