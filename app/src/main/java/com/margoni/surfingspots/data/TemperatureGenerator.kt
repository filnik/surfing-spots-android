package com.margoni.surfingspots.data

import kotlin.random.Random

interface TemperatureGenerator {
    suspend fun generate(): Int
}

class LocalTemperatureGenerator : TemperatureGenerator {

    override suspend fun generate(): Int {
        return Random.nextInt(MIN_TEMPERATURE, MAX_TEMPERATURE)
    }

    companion object {
        private const val MIN_TEMPERATURE = 0
        private const val MAX_TEMPERATURE = 60
    }
}

