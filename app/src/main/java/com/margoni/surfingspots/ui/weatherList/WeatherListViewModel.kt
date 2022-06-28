package com.margoni.surfingspots.ui.weatherList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.margoni.surfingspots.data.WeatherRepository
import com.margoni.surfingspots.data.WeathersData
import com.margoni.surfingspots.data.network.NetworkException
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper
import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.GenericError
import com.margoni.surfingspots.ui.weatherList.model.NetworkError
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState
import com.margoni.surfingspots.utils.Constants.THREE_ATTEMPTS
import com.margoni.surfingspots.utils.Constants.THREE_SECONDS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val repository: WeatherRepository,
    private val mapper: WeatherListUiStateMapper,
    private val retryAttempts: Long = THREE_ATTEMPTS,
    private val timeToWaitBeforeRetry: Long = THREE_SECONDS

) : ViewModel() {
    private val _uiState: MutableStateFlow<WeatherListUiState> =
        MutableStateFlow(WeatherListUiState())

    init {
        initialize()
    }

    val uiState: LiveData<WeatherListUiState> =
        this._uiState.asLiveData(viewModelScope.coroutineContext)

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
            .retryWhen { cause, attempt ->
                if (cause is NetworkException && attempt < retryAttempts) {
                    emitRetrying(attempt + 1)
                    delay(timeToWaitBeforeRetry)
                    return@retryWhen true
                }
                false
            }
            .catch { exception ->
                val error = mapErrorFrom(exception)
                emitFailure(error)
            }.collect { data ->
                when (data) {
                    is WeathersData.Data -> emitSuccess(mapper.map(data.list))
                    is WeathersData.Fetching -> emitLoading(data.status)
                }
            }
    }

    private suspend fun emitSuccess(list: List<WeatherUiState>) = with(_uiState) {
        emit(value.success(list))
    }

    private suspend fun emitFailure(error: Error) = with(_uiState) {
        emit(value.failure(error))
    }

    private suspend fun emitRetrying(attempt: Long) = with(_uiState) {
        emit(value.retrying(attempt))
    }

    private suspend fun emitLoading(status: Boolean) = with(_uiState) {
        emit(value.loading(status))
    }

    private fun mapErrorFrom(exception: Throwable): Error {
        return when (exception) {
            is NetworkException -> NetworkError()
            else -> GenericError()
        }
    }

}
