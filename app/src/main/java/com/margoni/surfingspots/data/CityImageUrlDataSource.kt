package com.margoni.surfingspots.data

interface CityImageUrlDataSource {
    fun imageFor(city: String): String
}