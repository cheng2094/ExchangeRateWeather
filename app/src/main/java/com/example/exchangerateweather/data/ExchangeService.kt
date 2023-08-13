package com.example.exchangerateweather.data

import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.domain.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExchangeService {
    @POST(Constants.EXCHANGE_PATH)
    suspend fun getWeather(
        @Query(Constants.LATITUDE_PARAM) lat: Double,
        @Query(Constants.LONGITUDE_PARAM) lon: Double,
        @Query(Constants.APP_ID_PARAM) appId: String
    ) : WeatherResponse
}