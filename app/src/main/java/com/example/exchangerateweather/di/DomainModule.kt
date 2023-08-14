package com.example.exchangerateweather.di

import com.example.exchangerateweather.data.RetrofitDataSource
import com.example.exchangerateweather.domain.interfaces.WeatherDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun provideWeatherDataSource(retrofitDataSource: RetrofitDataSource) : WeatherDataSource
}