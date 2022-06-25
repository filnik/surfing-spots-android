package com.margoni.surfingspots.data

import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MAX_TEMPERATURE
import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MIN_TEMPERATURE
import kotlin.random.Random

class LocalRandomTemperatureGenerator : RandomTemperatureGenerator {

    override suspend fun generate(): Int {
        return Random.nextInt(MIN_TEMPERATURE, MAX_TEMPERATURE)
    }

}