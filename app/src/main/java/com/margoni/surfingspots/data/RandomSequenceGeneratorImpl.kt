package com.margoni.surfingspots.data

import kotlin.random.Random

class RandomSequenceGeneratorImpl: RandomSequenceGenerator {
    override fun generate(size: Int): List<Int> {
        return (0 until size).toList().shuffled()
    }

    override fun nextSequence(from: List<Int>): List<Int> {
        val lastElement = from.last()
        return from.dropLast(1)
            .shuffled()
            .toMutableList()
            .also {
                it.add(Random.nextInt(1, it.size), lastElement)
            }
    }

}