package com.margoni.surfingspots.ui.weatherList

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.margoni.surfingspots.databinding.WeatherItemBinding
import com.margoni.surfingspots.ui.weatherList.model.WeatherUiState

class WeatherListAdapter :
    ListAdapter<WeatherUiState, WeatherListAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    class WeatherViewHolder(
        private val binding: WeatherItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherUiState) {
            binding.apply {
                cityName.text = weather.city
                weatherDescription.text = weather.description
                cityImage.foreground =
                    ColorDrawable(ContextCompat.getColor(context, weather.foregroundColor))

                Glide.with(context)
                    .load(weather.imageUrl)
                    .centerCrop()
                    .into(cityImage)

                lastItemMarginBottom.isVisible = weather.isLast
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

    private class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherUiState>() {

        override fun areItemsTheSame(oldItem: WeatherUiState, newItem: WeatherUiState): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: WeatherUiState, newItem: WeatherUiState): Boolean {
            return oldItem == newItem
        }

    }
}