package com.margoni.surfingspots

data class Weather(val city: String, val degrees: Int, val imageUrl: String) {
    val isSunny: Boolean
        get() = degrees >= 30
}