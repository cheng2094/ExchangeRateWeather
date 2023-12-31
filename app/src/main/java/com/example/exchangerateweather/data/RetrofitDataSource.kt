package com.example.exchangerateweather.data

import com.example.exchangerateweather.domain.interfaces.WeatherDataSource
import com.example.exchangerateweather.domain.models.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RetrofitDataSource @Inject constructor(val api: WeatherService) : WeatherDataSource{

    //Implementation of getWeather
    override suspend fun getWeather(lat: Double, lon: Double, appid: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            api.getWeather(lat, lon, appid)
        }
}