package com.example.exchangerateweather.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cursosandroidant.weather.common.utils.Constants.CHANNEL_ID
import com.cursosandroidant.weather.common.utils.Constants.CHANNEL_NAME
import com.cursosandroidant.weather.common.utils.Constants.NOTIF_ID
import com.example.exchangerateweather.R
import com.example.exchangerateweather.databinding.FragmentLoginBinding

/**
 * Author: Sheng Hsuen
 * Date: 12/08/2023
 *
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)

        createNotifChannel()

        val intent= activity?.intent
        var pendingIntent: PendingIntent? = null
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notif = context?.let {
            NotificationCompat.Builder(it,CHANNEL_ID)
                .setContentTitle("Exchange Rate Weather")
                .setContentText("Hi, you are logged in Exchange Rate Weather")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        binding.btnLogin.setOnClickListener{
            if(binding.inputUsername.text.isNullOrEmpty()){
                val dialogBuilder = AlertDialog.Builder(this.requireContext())
                dialogBuilder.setMessage("Invalid username, please try again")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, id ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle("Error")
                alert.show()
            }else{
                //Check if the device already have a permission
                if (context?.let { it1 -> ActivityCompat.checkSelfPermission(it1, Manifest.permission.POST_NOTIFICATIONS)
                    } == PackageManager.PERMISSION_GRANTED
                ) {
                    //Send notification
                    if (notif != null) {
                        context?.let { NotificationManagerCompat.from(it) }?.notify(NOTIF_ID, notif)
                    }
                }
                //Go to Detail screen
                val directions = LoginFragmentDirections.actionLoginFragmentToDetailFragment(binding.inputUsername.text.toString())
                findNavController().navigate(directions)
            }
        }
    }
    private fun createNotifChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}