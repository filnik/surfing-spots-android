package com.margoni.surfingspots.data.network.client

import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.margoni.surfingspots.data.network.model.CityApiResponse

class CitiesApiClient : CitiesApi {

    private val response = CitiesApiResponse(
        listOf(
            CityApiResponse("Cuba"),
            CityApiResponse("Los Angeles"),
            CityApiResponse("Miami"),
            CityApiResponse("Porto"),
            CityApiResponse("Ortona"),
            CityApiResponse("Riccione"),
            CityApiResponse("Midgar")
        )
    )

    override suspend fun fetch(): CitiesApiResponse {
        return response
    }

}