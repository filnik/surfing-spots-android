package com.margoni.surfingspots.data.network.client

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class HttpClientImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): HttpClient {

    override suspend fun get(url: String, params: Map<String, String>): String? = withContext(ioDispatcher) {
        val url = if (params.isEmpty()) url else "$url?${urlEncode(params)}"

        val connection = URL(url).openConnection() as HttpURLConnection

        connection.requestMethod = HttpMethod.GET

        return@withContext connection.inputStream?.bufferedReader()?.readText()
    }

    private fun urlEncode(params: Map<String, String>): String {
        return params
            .map { "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}" }
            .joinToString("&")
    }
}