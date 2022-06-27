package com.margoni.surfingspots.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LocalRandomTemperatureGeneratorTest {

    @Test
    fun `generate random temperature value`() = runTest {
        val generator = LocalRandomTemperatureGenerator()

        val values = (0 until 100).map { generator.generate() }

        assertThat(values).allMatch { it in 0..60 }
        assertThat(values.distinct().size).isGreaterThan(1)
    }
}