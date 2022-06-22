package com.margoni.surfingspots

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val sampleList = listOf<Weather>(
        Weather("Trento", 29),
        Weather("Roma", 31)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weatherList.apply {
            adapter = WeatherListAdapter(sampleList)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }


}