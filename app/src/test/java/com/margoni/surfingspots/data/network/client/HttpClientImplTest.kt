package com.margoni.surfingspots.data.network.client

import com.margoni.surfingspots.data.network.NetworkException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
class HttpClientImplTest {
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `make get http request successfully`() = runTest(testDispatcher) {
        val response = HttpClientImpl(testDispatcher).get("https://developer.android.com/")
        assertThat(response).isNotEmpty()
    }

    @Test
    fun `network exception when get http request fails`() = runTest(testDispatcher) {
        assertThrows<NetworkException> {
            HttpClientImpl(testDispatcher).get("non-existent.domain")
        }
    }

}