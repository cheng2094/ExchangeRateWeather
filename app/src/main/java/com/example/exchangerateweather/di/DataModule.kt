package com.example.exchangerateweather.di

import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.data.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @WeatherRetrofit
    fun provideWeatherRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @ExchangeRetrofit
    fun provideExchangeRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.EXCHANGE_BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

    @Provides
    fun provideWeatherServices(@WeatherRetrofit retrofit: Retrofit):WeatherService =
        retrofit.create(WeatherService::class.java)

    @Provides
    fun provideExchangeServices(@ExchangeRetrofit retrofit: Retrofit):WeatherService =
        retrofit.create(WeatherService::class.java)
}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExchangeRetrofit