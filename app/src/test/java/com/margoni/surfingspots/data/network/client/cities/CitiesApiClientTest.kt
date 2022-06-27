package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.data.network.NetworkException
import com.margoni.surfingspots.data.network.client.HttpClient
import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.margoni.surfingspots.data.network.model.CityApiResponse
import com.margoni.surfingspots.data.network.utils.JsonDeserializer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
@ExperimentalCoroutinesApi
class CitiesApiClientTest {
    private val testDispatcher = StandardTestDispatcher()
    private val httpClient = mockk<HttpClient>()
    private val jsonDeserializer = mockk<JsonDeserializer>()
    private val client = CitiesApiClient(httpClient, jsonDeserializer, "endpoint", testDispatcher)

    @Test
    fun `retrieve a response successfully`() = runTest(testDispatcher) {
        val expected = CitiesApiResponse(listOf(CityApiResponse("cityName")))
        coEvery { httpClient.get("endpoint") } returns "anyCitiesJsonResponse"
        every { jsonDeserializer.from("anyCitiesJsonResponse", CitiesApiResponse::class.java) }returns expected
        val actual = client.fetch()

        assertEquals(expected, actual)
    }

    @Test
    fun `propagate exception from http client`() = runTest(testDispatcher) {
        coEvery { httpClient.get("endpoint") } throws NetworkException()

        assertThrows<NetworkException> {
            client.fetch()
        }
    }
}