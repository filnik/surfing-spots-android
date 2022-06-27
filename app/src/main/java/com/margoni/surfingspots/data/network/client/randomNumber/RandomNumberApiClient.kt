package com.margoni.surfingspots.data.network.client.randomNumber

import com.margoni.surfingspots.BuildConfig
import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class RandomNumberApiClient(
    private val client: HttpClient,
    private val endpoint: String = BuildConfig.RANDOM_NUMBER_ENDPOINT,
    private val defaultDispatcher: CoroutineDispatcher = Default
) : RandomNumberApi {

    override suspend fun fetch(min: Int, max: Int): RandomNumberApiResponse = withContext(defaultDispatcher) scope@{
        val body = client.get(
            url = endpoint,
            params = mapOf("min" to min.toString(), "max" to max.toString())
        )
        return@scope RandomNumberApiResponse(body)
    }

}

