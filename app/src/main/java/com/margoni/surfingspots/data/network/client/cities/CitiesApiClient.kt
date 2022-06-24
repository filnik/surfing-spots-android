package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.BuildConfig
import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class CitiesApiClient(
    private val client: HttpClient,
    private val endpoint: String = BuildConfig.CITIES_API_ENDPOINT,
    private val ioDispatcher: CoroutineDispatcher = IO
) : CitiesApi {

    override suspend fun fetch(): CitiesApiResponse = withContext(ioDispatcher) {
        val json = client.get(endpoint)

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val jsonAdapter = moshi.adapter(CitiesApiResponse::class.java)

        return@withContext jsonAdapter.fromJson(json!!)!!
    }

}

