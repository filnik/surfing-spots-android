package com.margoni.surfingspots.ui.weatherList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding
import com.margoni.surfingspots.factory.Factory

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
            WeatherListViewModelFactory(Factory.WeatherRepository())
        }

        viewModel.list().observe(this) { list ->
            println(list.map { it.temperature })
            weatherListAdapter.submitList(list)
        }
    }

}

