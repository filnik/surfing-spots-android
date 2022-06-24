package com.margoni.surfingspots.domain.model

data class Weather(val city: City, val temperature: Int) {
    val isSunny: Boolean
        get() = temperature >= 30
}