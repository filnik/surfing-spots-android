package com.margoni.surfingspots.data.network.client

import com.margoni.surfingspots.data.network.model.CitiesApiResponse

interface CitiesApi {
    suspend fun fetch(): CitiesApiResponse
}