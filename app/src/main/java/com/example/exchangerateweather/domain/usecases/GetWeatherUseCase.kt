package com.example.exchangerateweather.domain.usecases

import com.example.exchangerateweather.domain.interfaces.WeatherDataSource
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherDataSource: WeatherDataSource) {
    suspend operator fun invoke(lat: Double, lon: Double, appid: String) = weatherDataSource.getWeather(lat, lon, appid)
}