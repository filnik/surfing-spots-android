package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.BuildConfig
import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.margoni.surfingspots.data.network.utils.JsonDeserializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class CitiesApiClient(
    private val client: HttpClient,
    private val deserializer: JsonDeserializer,
    private val endpoint: String = BuildConfig.CITIES_API_ENDPOINT,
    private val defaultDispatcher: CoroutineDispatcher = Default
) : CitiesApi {

    override suspend fun fetch(): CitiesApiResponse = withContext(defaultDispatcher) {
        val json = client.get(endpoint)
        val response = deserializer.from(json!!, CitiesApiResponse::class.java)
        return@withContext response
    }

}

