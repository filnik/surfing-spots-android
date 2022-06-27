package com.margoni.surfingspots.data

import com.margoni.surfingspots.CitiesApiResponseBuilder
import com.margoni.surfingspots.data.network.client.cities.CitiesApi
import com.margoni.surfingspots.domain.model.City
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CityDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val citiesApi = mockk<CitiesApi>()
    private val imagesUrlDataSource = mockk<CityImageUrlDataSource>()

    @Test
    fun `fetch list from api successfully`() = runTest(testDispatcher) {
        val response = CitiesApiResponseBuilder().with("cityA", "cityB").build()
        coEvery { citiesApi.fetch() } returns response
        every { imagesUrlDataSource.imageFor("cityA") } returns "cityAImageUrl"
        every { imagesUrlDataSource.imageFor("cityB") } returns "cityBImageUrl"

        val actual = CityDataSourceImpl(citiesApi, imagesUrlDataSource).list()

        assertThat(actual).containsExactly(
            City("cityA", "cityAImageUrl"),
            City("cityB", "cityBImageUrl")
        )
    }

}

