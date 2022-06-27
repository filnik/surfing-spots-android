package com.margoni.surfingspots.data.network.client.cities

import com.margoni.surfingspots.data.network.model.CitiesApiResponse
import com.margoni.surfingspots.data.network.model.CityApiResponse
import com.margoni.surfingspots.data.network.utils.MoshiJsonDeserializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CitiesJsonDeserializerTest {

    @Test
    fun `deserialize cities json successfully`() {
        val expected = CitiesApiResponse(listOf(CityApiResponse("Cuba"), CityApiResponse("Los Angeles"), CityApiResponse("Miami")))
        val json = """{"cities": 
                |[
                    |{"name": "Cuba"}, 
                    |{"name": "Los Angeles"}, 
                    |{"name": "Miami"}
                |]
            |}""".trimMargin()

        val actual = MoshiJsonDeserializer().from(json, CitiesApiResponse::class.java)

        assertThat(actual).isEqualTo(expected)
    }

}