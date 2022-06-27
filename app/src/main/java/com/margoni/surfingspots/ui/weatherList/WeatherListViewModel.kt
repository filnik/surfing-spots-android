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
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
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
        MutableStateFlow(WeatherListUiState())

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
        emitLoading()
        repository.fetch()
            .map { mapper.map(it) }
            .retryWhen(handleRetry())
            .catch { exception ->
                val error = mapErrorFrom(exception)
                emitFailure(error)
            }.collect { list ->
                emitSuccess(list)
            }
    }

    private fun handleRetry(): suspend FlowCollector<List<WeatherUiState>>.(cause: Throwable, attempt: Long) -> Boolean =
        retry@{ cause, attempt ->
            if (cause is NetworkException && attempt < retryAttempts) {
                emitRetrying(attempt + 1)
                delay(timeToWaitAfterError)
                return@retry true
            }
            false
        }

    private suspend fun emitSuccess(list: List<WeatherUiState>) {
        uiState.emit(uiState.value.success(list))
    }

    private suspend fun emitFailure(error: Error) {
        uiState.emit(uiState.value.failure(error))
    }

    private suspend fun emitRetrying(attempt: Long) {
        uiState.emit(uiState.value.retrying(attempt))
    }

    private suspend fun emitLoading() {
        uiState.emit(uiState.value.loading())
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
