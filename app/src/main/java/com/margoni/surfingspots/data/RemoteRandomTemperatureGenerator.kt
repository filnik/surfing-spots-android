package com.margoni.surfingspots.data

import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MAX_TEMPERATURE
import com.margoni.surfingspots.data.RandomTemperatureGenerator.Companion.MIN_TEMPERATURE
import com.margoni.surfingspots.data.network.client.randomNumber.RandomNumberApi
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class RemoteRandomTemperatureGenerator(
    private val randomNumberApi: RandomNumberApi,
    private val defaultDispatcher: CoroutineDispatcher = Default
) : RandomTemperatureGenerator {

    override suspend fun generate(): Int = withContext(defaultDispatcher) scope@{
        val response = randomNumberApi.fetch(MIN_TEMPERATURE, MAX_TEMPERATURE)
        return@scope parseNumberFrom(response)
    }

    private fun parseNumberFrom(response: RandomNumberApiResponse) =
        response.body.takeWhile { it.isDigit() }.toInt()

}