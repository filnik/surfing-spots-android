package com.margoni.surfingspots.data.network.model

import com.squareup.moshi.Json

data class CityApiResponse(
    @Json(name = "name") val name: String
)

data class CitiesApiResponse(
    @Json(name = "cities") val cities: List<CityApiResponse>
)
