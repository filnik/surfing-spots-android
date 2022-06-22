package com.margoni.surfingspots

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.margoni.surfingspots.databinding.WeatherItemBinding

data class Weather(val city: String, val degrees: Int) {
    val isSunny: Boolean
        get() = degrees >= 30
}

class WeatherListAdapter(private val weatherList: List<Weather>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(
        private val binding: WeatherItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.apply {
                cityName.text = weather.city
                weatherDescription.text =
                    "${if (weather.isSunny) "Sunny" else "Cloudy"} - ${weather.degrees} degrees"
                val foregroundColor =
                    if (weather.isSunny) R.color.sunny_foreground else R.color.cloudy_foreground
                cityBackground.foreground =
                    ColorDrawable(ContextCompat.getColor(context, foregroundColor))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

}