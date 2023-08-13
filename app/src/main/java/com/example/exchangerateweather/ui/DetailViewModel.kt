package com.example.exchangerateweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateweather.data.RetrofitDataSource
import com.example.exchangerateweather.domain.models.WeatherResponse
import com.example.exchangerateweather.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Sheng Hsuen
 * Date: 13/08/2023
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val weather = MutableLiveData<WeatherResponse>()
    val dataLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(lat: Double, lon: Double, appId: String){
        getWeather(lat, lon, appId)
    }
    private fun getWeather(lat: Double, lon: Double, appId: String){
        viewModelScope.launch {
            try {
                val retrievedUser = getWeatherUseCase(lat, lon, appId)
                weather.postValue(retrievedUser)

            } catch (e: Exception) {
                weather.postValue(WeatherResponse())
            } finally {
            }
        }
    }
}