package com.margoni.surfingspots.ui.weatherList

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding
import com.margoni.surfingspots.factory.Factory
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapper
import com.margoni.surfingspots.ui.weatherList.mapper.WeatherListUiStateMapperImpl

class WeatherListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherListBinding
    private val weatherListAdapter = WeatherListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weatherList.apply {
            adapter = weatherListAdapter
            layoutManager = LinearLayoutManager(this@WeatherListActivity)
        }

        val viewModel: WeatherListViewModel by viewModels {
            WeatherListViewModelFactory(Factory.WeatherRepository(), WeatherListUiStateMapperImpl())
        }

        viewModel.list.observe(this) { uiState ->
            when (uiState) {
                is WeatherListUiState.Success -> weatherListAdapter.submitList(uiState.list)
                is WeatherListUiState.Error -> showError(uiState.exception)
            }
        }
    }

    private fun showError(exception: Throwable) {
        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
    }

}

