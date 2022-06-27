package com.margoni.surfingspots.ui.weatherList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.margoni.surfingspots.R
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding
import com.margoni.surfingspots.factory.Factory
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapperImpl
import com.margoni.surfingspots.ui.weatherList.model.Error

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

        viewModel.list.observe(this) { uiState ->
            updateUiState(uiState)
        }
    }

    private fun updateUiState(uiState: WeatherListUiState) {
        with(uiState) {
            weatherListAdapter.submitList(uiState.list)
            if (isRetrying) showRetryingMessage(attempt)
            if (error != null) showErrorDialog(error)
        }
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
            .setNegativeButton("close app") { _, _ -> finish() }
            .setPositiveButton("resume") { _, _ -> viewModel.resume() }
            .show()
    }

}

