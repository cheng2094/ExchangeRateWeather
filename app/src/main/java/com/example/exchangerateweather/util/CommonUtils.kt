package com.example.exchangerateweather.util

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cursosandroidant.weather.common.utils.Constants
import com.example.exchangerateweather.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object CommonUtils {

    //Send notification
    fun sendNotification(
        activity:FragmentActivity,
        context:Context,
        username:String,
        purchase:String,
        sale:String,
        temp:String
    ){
        //Checking version to use Notification
        val intent= activity.intent
        val pendingIntent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //Content of the notification
        val notif = context.let {
            NotificationCompat.Builder(it, Constants.CHANNEL_ID)
                .setContentTitle("Exchange Rate Weather")
                .setContentText("Hi ${username}, today purchase is $purchase, Sale is $sale, and the temperature is $temp")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        //Check if the device already have a permission
        if (context.let { it1 -> ActivityCompat.checkSelfPermission(it1, Manifest.permission.POST_NOTIFICATIONS)
            } == PackageManager.PERMISSION_GRANTED
        ) {
            //Send notification
            context.let { NotificationManagerCompat.from(it) }.notify(Constants.NOTIF_ID, notif)
        }
    }

    //Function to get value of Exchange
    fun ExchangeResponseFilter(result: String):String {
     lateinit var resultValue:String
        val startIndex = result.indexOf("NUM_VALOR")
        if (startIndex != -1) {
            val endIndex = startIndex + 16
            resultValue = result.substring(startIndex, endIndex)
        }
     return resultValue.replace("NUM_VALOR=", "")
    }

    //Convert  Fahrenheit to Celsius
    fun FahrenheitToCelsius(kelvin:Double):Double{
        return kelvin - 273.15
    }

    //Format to Hour and min
    fun getHour(epoch: Long): String = getFormatedTime(epoch)

    //Get current date
    fun getDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDateTime.now().format(formatter)
    }

    private fun getFormatedTime(epoch: Long): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(epoch * 1_000)
    }


    //Extends functions
    fun ImageView.loadImage(iconName: String?) {
        //Assemble the uri
        val uri = Constants.ICON_URL + iconName + Constants.ICON_URL_EXTENSION
        val options = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
    }
    fun Double.decimalFormat(digits: Int) = "%.${digits}fºC".format(this)


}