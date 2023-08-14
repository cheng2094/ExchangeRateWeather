package com.example.exchangerateweather.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateweather.util.CommonUtils.ExchangeResponseFilter
import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.data.ExchangeServiceSoap
import com.example.exchangerateweather.domain.models.WeatherResponse
import com.example.exchangerateweather.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Sheng Hsuen
 * Date: 13/08/2023
 */
//Use Hilt to Inject GetWeatherUseCase
@HiltViewModel
class DetailViewModel @Inject constructor(
    val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val weather = MutableLiveData<WeatherResponse>()
    val dataLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val purchase = MutableLiveData<String>()
    val sale = MutableLiveData<String>()

    fun refresh(lat: Double, lon: Double, appId: String, indicator:String) {
        getWeather(lat, lon, appId, indicator)
    }

    //Call UseCase to get Weather, If success, then call getExchange to get Purchase
    private fun getWeather(lat: Double, lon: Double, appId: String, indicator:String) {
        viewModelScope.launch {
            try {
                val retrievedUser = getWeatherUseCase(lat, lon, appId)
                weather.postValue(retrievedUser)
                getExchange(indicator)

            } catch (e: Exception) {
                weather.postValue(WeatherResponse())
                dataLoadError.postValue(true)
                loading.postValue(false)
            } finally {
            }
        }
    }

    //Call ExchangeServiceSoap to get Exchange
    private fun getExchange(indicator: String) {
        viewModelScope.launch {
            val exchangeServiceSoap = ExchangeServiceSoap()
            exchangeServiceSoap.getExchange(
                indicator,
                object : ExchangeServiceSoap.Callback {

                    //When Success
                    @SuppressLint("SuspiciousIndentation")
                    override fun onSuccess(result: String) {
                        //Valid if result contain Purchase code (317)
                        if (result.contains(Constants.INDICATOR_PURCHASE)) {
                            purchase.postValue(ExchangeResponseFilter(result))
                            //Call again getExchange to get Sale
                            getExchange(Constants.INDICATOR_SALE)
                        }else
                            sale.postValue(ExchangeResponseFilter(result))
                            dataLoadError.postValue(false)
                            loading.postValue(false)
                    }
                    //When fail show error message
                    override fun onError(error: Exception) {
                        dataLoadError.postValue(true)
                        loading.postValue(false)
                    }
                })
        }
    }
}