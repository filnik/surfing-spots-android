package com.margoni.surfingspots.ui.weatherList.model

interface Error {
    val title: String
    val message: String
}

data class NetworkError(
    override val title: String = "Network Error",
    override val message: String = "Unable to fetch data from remote services"
) : Error

data class GenericError(
    override val title: String = "Unknown Error",
    override val message: String = "Something went wrong..."
) : Error
