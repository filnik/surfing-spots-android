package com.margoni.surfingspots.data

import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MAX_TEMPERATURE
import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MIN_TEMPERATURE
import com.margoni.surfingspots.data.network.client.randomNumber.RandomNumberApi
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse

class RemoteRandomTemperatureGenerator(
    private val randomNumberApi: RandomNumberApi
) : RandomTemperatureGenerator {

    override suspend fun generate(): Int {
        val response = randomNumberApi.fetch(MIN_TEMPERATURE, MAX_TEMPERATURE)
        return parseNumberFrom(response)
    }

    private fun parseNumberFrom(response: RandomNumberApiResponse) =
        response.body.takeWhile { it.isDigit() }.toInt()

}