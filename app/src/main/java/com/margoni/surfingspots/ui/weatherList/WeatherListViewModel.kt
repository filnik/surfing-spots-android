package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.data.network.NetworkException
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper
import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.GenericError
import com.margoni.surfingspots.ui.weatherList.model.NetworkError
import com.margoni.surfingspots.utils.Constants.THREE_ATTEMPTS
import com.margoni.surfingspots.utils.Constants.THREE_SECONDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val repository: WeatherRepository,
    private val mapper: WeatherListUiStateMapper,
    private val retryAttempts: Long = THREE_ATTEMPTS,
    private val timeToWaitAfterError: Long = THREE_SECONDS

) : ViewModel() {
    private val uiState: MutableStateFlow<WeatherListUiState> =
        MutableStateFlow(WeatherListUiState.Success(emptyList()))

    init {
        initialize()
    }

    fun resume() {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        repository.fetch()
            .map { mapper.map(it) }
            .retryWhen { cause, attempt ->
                if (attempt < retryAttempts && cause is NetworkException) {
                    uiState.value = WeatherListUiState.Retrying(attempt + 1)
                    delay(timeToWaitAfterError)
                    return@retryWhen true
                }
                false
            }
            .catch { exception ->
                val error = mapErrorFrom(exception)
                uiState.value = WeatherListUiState.Failure(error)
            }.collect {
                uiState.value = WeatherListUiState.Success(it)
            }
    }

    private fun mapErrorFrom(exception: Throwable): Error {
        return when (exception) {
            is NetworkException -> NetworkError()
            else -> GenericError()
        }
    }

    val list: LiveData<WeatherListUiState> =
        this.uiState.asLiveData(viewModelScope.coroutineContext)

}
