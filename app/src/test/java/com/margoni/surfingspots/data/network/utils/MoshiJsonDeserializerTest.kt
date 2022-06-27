package com.margoni.surfingspots.data.network.utils

import com.squareup.moshi.Json
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class MoshiJsonDeserializerTest {

    @Test
    fun `deserialize json successfully`() {
        val expected = TestJsonObject(TestInternalJsonObject(listOf("A", "B", "C")))

        val actual = MoshiJsonDeserializer().from(
            """{"object":{"array":["A","B","C"]}}""",
            TestJsonObject::class.java
        )

        assertThat(actual).isEqualTo(expected)
    }

}

data class TestJsonObject (
    @Json(name = "object") val `object`: TestInternalJsonObject
)

data class TestInternalJsonObject(
    @Json(name = "array") val array: List<String>
)