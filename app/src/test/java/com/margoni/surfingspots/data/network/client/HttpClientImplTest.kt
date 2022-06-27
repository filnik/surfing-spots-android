package com.margoni.surfingspots.data.network.client

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class HttpClientImplTest {
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `make get http request successfully`() = runTest(testDispatcher) {
        val response = HttpClientImpl(testDispatcher).get("https://developer.android.com/")
        assertThat(response).isNotEmpty()
    }

}