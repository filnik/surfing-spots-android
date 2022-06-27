package com.margoni.surfingspots

import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.margoni.surfingspots.data.network.model.CityApiResponse

class CitiesApiResponseBuilder {
    private var list: List<String> = emptyList()

    fun with(vararg list: String): CitiesApiResponseBuilder {
        this.list = list.toList()
        return this
    }

    fun build(): CitiesApiResponse {
        return CitiesApiResponse(cities = list.map { CityApiResponse(it) })
    }
}