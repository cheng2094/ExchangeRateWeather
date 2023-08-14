package com.cursosandroidant.weather.common.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exchangerateweather.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object CommonUtils {
 fun getProgressDrawable(context: Context): CircularProgressDrawable {
  return CircularProgressDrawable(context).apply{
   strokeWidth = 10f
   centerRadius = 50f
   start()
  }
 }

 private fun getFormatedTime(epoch: Long, pattern: String): String {
  return SimpleDateFormat(pattern, Locale.getDefault()).format(epoch * 1_000)
 }

 //Extends functions
 fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
  val options = RequestOptions()
   .placeholder(progressDrawable)
   .error(R.mipmap.ic_launcher_round)
  Glide.with(this.context)
   .setDefaultRequestOptions(options)
   .load(uri)
   .into(this)
 }

 fun Double.decimalFormat(digits: Int) = "%.${digits}fºC".format(this)

 fun getHour(epoch: Long): String = getFormatedTime(epoch, "HH:mm")

 fun getDate():String{
  val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
  return LocalDateTime.now().format(formatter)
 }

}