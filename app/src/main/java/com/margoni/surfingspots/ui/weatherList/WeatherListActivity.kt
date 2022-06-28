package com.margoni.surfingspots.ui.weatherList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.R
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding
import com.margoni.surfingspots.factory.Factory
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapperImpl
import com.margoni.surfingspots.ui.weatherList.model.Error
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

class WeatherListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherListBinding

    private val viewModel by viewModels<WeatherListViewModel> {
        WeatherListViewModelFactory(Factory.WeatherRepository(), WeatherListUiStateMapperImpl())
    }

    private val weatherListAdapter = WeatherListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weatherList.apply {
            adapter = weatherListAdapter
            layoutManager = LinearLayoutManager(this@WeatherListActivity)
        }

        viewModel.uiState.observe(this) { uiState ->
            updateUi(uiState)
        }
    }

    private fun updateUi(state: WeatherListUiState) = with(state) {
        updateList(state.list)
        setLoaderVisibility(isLoading)
        if (isRetrying) showRetryingMessage(attempt)
        if (error != null) showErrorDialog(error)
    }

    private fun updateList(list: List<WeatherUiState>) {
        weatherListAdapter.submitList(list)
    }

    private fun setLoaderVisibility(isVisible: Boolean) {
        binding.loader.isVisible = isVisible
    }

    private fun showRetryingMessage(attempt: Long) {
        Toast.makeText(
            applicationContext,
            getString(R.string.retrying_message, attempt),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorDialog(error: Error) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle(error.title)
            .setMessage(error.message)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.close_app_button_title)) { _, _ -> finish() }
            .setPositiveButton(getString(R.string.resume_button_title)) { _, _ -> viewModel.resume() }
            .show()
    }

}

