package com.margoni.surfingspots.data.network.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MoshiJsonDeserializer : JsonDeserializer {

    override fun <T> from(json: String, clazz: Class<T>): T {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter = moshi.adapter(clazz)

        return jsonAdapter.fromJson(json)!!
    }

}