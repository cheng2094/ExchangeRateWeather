package com.example.exchangerateweather.di

import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.data.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    //Provide Retrofit
    @Provides
    fun provideWeatherRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //Provide Services
    @Provides
    fun provideWeatherServices(retrofit: Retrofit):WeatherService =
        retrofit.create(WeatherService::class.java)
}