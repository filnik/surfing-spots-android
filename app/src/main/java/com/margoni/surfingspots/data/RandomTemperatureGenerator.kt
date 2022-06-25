package com.margoni.surfingspots.data

interface RandomTemperatureGenerator {
    suspend fun generate(): Int

    companion object {
        const val MIN_TEMPERATURE = 0
        const val MAX_TEMPERATURE = 60
    }
}

