package com.margoni.surfingspots.data

interface RandomSequenceGenerator {
    fun generate(size: Int): List<Int>
    fun nextSequence(from: List<Int>): List<Int>
}