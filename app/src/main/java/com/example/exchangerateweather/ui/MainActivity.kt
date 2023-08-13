package com.example.exchangerateweather.ui

import android.Manifest
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.exchangerateweather.R
import com.example.exchangerateweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //navController to navigation
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        //Inflate view with viewBinging
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //To check permission for notification
        setupPermissions()

        //Setup the Navigation and hide ActionBar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.hide();
        setupActionBarWithNavController(navController)
    }

    private fun setupPermissions() {
        //Valid if Api version is over 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //Check Notification permission
            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //Request allow the Notification permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}