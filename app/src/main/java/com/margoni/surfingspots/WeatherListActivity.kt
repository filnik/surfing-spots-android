package com.margoni.surfingspots

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.margoni.surfingspots.databinding.ActivityWeatherListBinding

class WeatherListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherListBinding

    private val sampleList = listOf(
        Weather("Roma", 31, "https://media-cdn.tripadvisor.com/media/photo-s/16/dd/3e/b1/el-coliseo-de-roma.jpg"),
        Weather("Trento", 30, "https://images.lonelyplanetitalia.it/static/places/trento-4682.jpg?q=90&p=2400%7C1350%7Cmax&s=8c97b377d7a989e311ffce0cd031b773"),
        Weather("Bolzano", 29, "https://images.placesonline.com/photos/guida_bolzano__1600332760.jpg?quality=80&w=700")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.weatherList.apply {
            adapter = WeatherListAdapter(sampleList)
            layoutManager = LinearLayoutManager(this@WeatherListActivity)
        }
    }

}