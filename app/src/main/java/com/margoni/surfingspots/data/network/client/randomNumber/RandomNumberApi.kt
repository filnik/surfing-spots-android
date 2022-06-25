package com.margoni.surfingspots.data.network.client.randomNumber

import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse

interface RandomNumberApi {
    suspend fun fetch(min: Int, max: Int): RandomNumberApiResponse
}
