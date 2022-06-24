package com.margoni.surfingspots.data.network.utils

interface JsonDeserializer {
    fun <T> from(json: String, clazz: Class<T>): T
}