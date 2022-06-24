package com.margoni.surfingspots.data.network.client

interface HttpClient {
    suspend fun get(url: String): String?
}