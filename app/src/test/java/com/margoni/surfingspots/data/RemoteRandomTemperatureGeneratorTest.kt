package com.margoni.surfingspots.data

import com.margoni.surfingspots.data.network.NetworkException
import com.margoni.surfingspots.data.network.client.randomNumber.RandomNumberApi
import com.margoni.surfingspots.data.network.model.RandomNumberApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
class RemoteRandomTemperatureGeneratorTest {
    private val testDispatcher = StandardTestDispatcher()
    private val randomNumberApi = mockk<RandomNumberApi>()
    private val generator = RemoteRandomTemperatureGenerator(randomNumberApi, testDispatcher)

    @Test
    fun `generate successfully`() = runTest(testDispatcher) {
        coEvery { randomNumberApi.fetch(0, 60) } returns RandomNumberApiResponse("11 is a number")

        val actual = generator.generate()

        assertThat(actual).isEqualTo(11)
    }

    @Test
    fun `propagate network exception`() = runTest(testDispatcher) {
        coEvery { randomNumberApi.fetch(0, 60) } throws NetworkException()

        assertThrows<NetworkException> { generator.generate() }
    }
}