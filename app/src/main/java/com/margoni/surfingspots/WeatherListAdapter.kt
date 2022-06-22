package com.margoni.surfingspots

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.margoni.surfingspots.databinding.WeatherItemBinding

class WeatherListAdapter :
    ListAdapter<Weather, WeatherListAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

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
                cityImage.foreground =
                    ColorDrawable(ContextCompat.getColor(context, foregroundColor))

                Glide.with(context)
                    .load(weather.imageUrl)
                    .centerCrop()
                    .into(cityImage);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {

        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

    }
}