package com.example.exchangerateweather.data

import com.example.exchangerateweather.util.CommonUtils
import com.cursosandroidant.weather.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE


class ExchangeServiceSoap {

    //Callback interface
    interface Callback {
        fun onSuccess(result: String)
        fun onError(error: Exception)
    }

    //Function to get data from SOAP Service
    fun getExchange(indicator: String, callback: Callback){

        //Assign parameters to send
        val request = SoapObject(Constants.EXCHANGE_BASE_URL, Constants.EXCHANGE_PATH)
        request.addProperty("Indicador", indicator)
        request.addProperty("FechaInicio", CommonUtils.getDate())
        request.addProperty("FechaFinal", CommonUtils.getDate())
        request.addProperty("Nombre", Constants.NAME)
        request.addProperty("SubNiveles", Constants.SUBLEVEL)
        request.addProperty("CorreoElectronico", Constants.EMAIL)
        request.addProperty("Token", Constants.TOKEN)

        //Send request
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = true
        envelope.setOutputSoapObject(request)

        //Use callback to handle response
        GlobalScope.launch(Dispatchers.IO) {
            val transport = HttpTransportSE(Constants.URL)
            try {
                transport.call(Constants.EXCHANGE_BASE_URL+"/"+Constants.EXCHANGE_PATH, envelope)
                val result = envelope.response.toString()
                callback.onSuccess(result)
            } catch (e: Exception) {
                e.printStackTrace()
                callback.onError(e)
            }
        }
    }
}