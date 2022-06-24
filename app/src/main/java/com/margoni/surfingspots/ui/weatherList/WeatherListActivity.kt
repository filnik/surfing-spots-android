package com.margoni.surfingspots.ui.weatherList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.data.CityDataSourceImpl
import com.margoni.surfingspots.data.WeatherRepositoryImpl
import com.margoni.surfingspots.data.network.client.cities.CitiesApiClient
import com.margoni.surfingspots.data.network.client.HttpClientImpl
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

        val viewModel: WeatherListViewModel by viewModels {
            WeatherListViewModelFactory(WeatherRepositoryImpl(CityDataSourceImpl(CitiesApiClient(
                HttpClientImpl(),
                endpoint = "https://run.mocky.io/v3/652ceb94-b24e-432b-b6c5-8a54bc1226b6"
            ))))
        }

        viewModel.list().observe(this) { list ->
            println(list.map { it.temperature })
            weatherListAdapter.submitList(list)
        }
    }

}