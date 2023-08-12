package com.example.exchangerateweather.domain.models

data class WeatherResponse(
    val id: Long? = null,
    val sys: Sys = Sys(),
    val main: Main = Main(),
    val weather: List<Weather> = emptyList(),
)
