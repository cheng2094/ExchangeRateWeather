package com.example.exchangerateweather.data

import com.cursosandroidant.weather.common.utils.CommonUtils
import com.cursosandroidant.weather.common.utils.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class ExchangeServiceSoap {

    fun getExchange(indicator: String): String {

        var request = SoapObject(Constants.EXCHANGE_BASE_URL, Constants.EXCHANGE_PATH)
        request.addProperty("Indicador", indicator)
        request.addProperty("FechaInicio", CommonUtils.getDate())
        request.addProperty("FechaFinal", CommonUtils.getDate())
        request.addProperty("Nombre", Constants.NAME)
        request.addProperty("SubNiveles", Constants.SUBLEVEL)
        request.addProperty("CorreoElectronico", Constants.EMAIL)
        request.addProperty("Token", Constants.TOKEN)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(request)

        val transport = HttpTransportSE(Constants.URL)
        transport.call(Constants.EXCHANGE_BASE_URL+"/"+Constants.EXCHANGE_PATH, envelope)

        val result = envelope.response as SoapPrimitive
        return result.toString()
    }
}