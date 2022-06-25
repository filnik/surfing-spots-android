package com.margoni.surfingspots.data.network.client.randomNumber

import com.margoni.surfingspots.BuildConfig
import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse

class RandomNumberApiClient(
    private val client: HttpClient,
    private val endpoint: String = BuildConfig.RANDOM_NUMBER_ENDPOINT
) : RandomNumberApi {

    override suspend fun fetch(min: Int, max: Int): RandomNumberApiResponse {
        val body = client.get(
            url = endpoint,
            params = mapOf("min" to min.toString(), "max" to max.toString())
        )
        return RandomNumberApiResponse(body!!)
    }

}

