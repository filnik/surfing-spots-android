package com.margoni.surfingspots.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RandomSequenceGeneratorImplTest {
    private val generator = RandomSequenceGeneratorImpl()

    @Test
    fun `random sequence generator`() {
        val sequences = (0 until 100).map { generator.generate(3) }

        sequences.forEach { assertThat(it).contains(0, 1, 2) }
        assertThat(sequences.distinct().size).isGreaterThan(1)
    }

    @Test
    fun `next random sequence not repeat last of old sequence and first of new sequence`() {
        val sequences = (0..100).map { generator.generate(3) }

        val listOfPairSequences = sequences.map { Pair(it, generator.nextSequence(it)) }

        listOfPairSequences.forEach {
            assertThat(it.first).containsAll(it.second)
            assertThat(it.first).doesNotContainSequence(it.second)
            assertThat(it.first.last()).isNotEqualTo(it.second.first())
        }
    }
}