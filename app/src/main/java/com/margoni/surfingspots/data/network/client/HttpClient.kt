package com.margoni.surfingspots.data.network.client

interface HttpClient {
    suspend fun get(url: String, params: Map<String, String> = emptyMap()): String
}