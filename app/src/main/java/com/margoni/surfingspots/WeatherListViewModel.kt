package com.margoni.surfingspots

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherListViewModel : ViewModel() {

    private val sampleList = listOf(
        Weather("Roma", 31, "https://media-cdn.tripadvisor.com/media/photo-s/16/dd/3e/b1/el-coliseo-de-roma.jpg"),
        Weather("Trento", 30, "https://images.lonelyplanetitalia.it/static/places/trento-4682.jpg?q=90&p=2400%7C1350%7Cmax&s=8c97b377d7a989e311ffce0cd031b773"),
        Weather("Bolzano", 29, "https://images.placesonline.com/photos/guida_bolzano__1600332760.jpg?quality=80&w=700")
    )

    fun list(): LiveData<List<Weather>> {
        return MutableLiveData<List<Weather>>().also {
            it.value = sampleList
        }
    }

}