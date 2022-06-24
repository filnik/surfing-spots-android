package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class CitiesApiClient(
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = IO
) : CitiesApi {

    override suspend fun fetch(): CitiesApiResponse = withContext(ioDispatcher) {
        val json = client.get("https://run.mocky.io/v3/652ceb94-b24e-432b-b6c5-8a54bc1226b6")

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val jsonAdapter = moshi.adapter(CitiesApiResponse::class.java)

        return@withContext jsonAdapter.fromJson(json!!)!!
    }

}

