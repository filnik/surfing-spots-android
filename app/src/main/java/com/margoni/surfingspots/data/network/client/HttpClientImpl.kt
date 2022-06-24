package com.margoni.surfingspots.data.network.client

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class HttpClientImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): HttpClient {

    override suspend fun get(url: String): String? = withContext(ioDispatcher) {
        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = HttpMethod.GET

        return@withContext connection.inputStream?.bufferedReader()?.readText()
    }
}