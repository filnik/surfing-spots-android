package com.margoni.surfingspots.data.network.client.randomNumber

import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class RandomNumberApiClientTest {
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `fetch random number response successfully`() = runTest(testDispatcher) {
        val httpClient = mockk<HttpClient>()
        val client = RandomNumberApiClient(httpClient, "randomNumberApiEndpoint", testDispatcher)
        coEvery {
            httpClient.get(
                "randomNumberApiEndpoint",
                mapOf("min" to "0", "max" to "60")
            )
        } returns "response message"

        val actual = client.fetch(0, 60)

        assertThat(actual).isEqualTo(RandomNumberApiResponse("response message"))
    }
}