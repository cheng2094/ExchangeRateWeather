package com.example.exchangerateweather.domain.interfaces

import com.example.exchangerateweather.domain.models.WeatherResponse

interface WeatherDataSource {

    suspend fun getWeather(lat: Double, lon: Double, appid: String): WeatherResponse
}