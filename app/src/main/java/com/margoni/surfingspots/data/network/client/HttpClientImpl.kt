package com.margoni.surfingspots.data.network.client

import com.margoni.surfingspots.data.network.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class HttpClientImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HttpClient {

    override suspend fun get(url: String, params: Map<String, String>): String =
        withContext(ioDispatcher) scope@{
            try {
                val url = if (params.isEmpty()) url else "$url?${urlEncode(params)}"

                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = HttpMethod.GET

                if (connection.responseCode != HTTP_OK) {
                    throw NetworkException()
                }

                return@scope connection.responseMessage()
            } catch (t: Throwable) {
                throw NetworkException()
            }
        }

    private fun HttpURLConnection.responseMessage(): String {
        return inputStream?.bufferedReader()?.readText()!!
    }

    private fun urlEncode(params: Map<String, String>): String {
        return params
            .map { "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.name())}" }
            .joinToString("&")
    }
}