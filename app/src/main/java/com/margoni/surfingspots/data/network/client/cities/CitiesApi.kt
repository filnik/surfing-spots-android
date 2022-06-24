package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.data.network.model.CitiesApiResponse

interface CitiesApi {
    suspend fun fetch(): CitiesApiResponse
}