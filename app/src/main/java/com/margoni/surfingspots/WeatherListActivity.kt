package com.margoni.surfingspots

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding

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

        val viewModel: WeatherListViewModel by viewModels()

        viewModel.list().observe(this) { list ->
            weatherListAdapter.submitList(list)
        }
    }

}