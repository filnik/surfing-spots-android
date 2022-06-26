package com.margoni.surfingspots

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.awaitAll

object TestUtils {

    suspend fun <T> valueObservedFrom(liveData: LiveData<T>): T {
        val completableDeferred = CompletableDeferred<T>()
        liveData.observeForever {
            completableDeferred.complete(it)
        }

        return completableDeferred.await()
    }

}