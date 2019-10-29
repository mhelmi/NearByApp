package com.github.mhelmi.nearby.features.nearbyplaces.ui.services

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.github.mhelmi.nearby.R
import com.github.mhelmi.nearby.utils.LOCATION_EXTRA
import com.github.mhelmi.nearby.utils.LOCATION_UPDATES
import com.github.mhelmi.nearby.utils.extentions.*
import com.google.android.gms.location.*

class TrackingService : Service() {
    private lateinit var client: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        startNotification()
        requestLocationUpdates()
    }

    private fun startNotification() {
        val channelId = createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH,
            true,
            getString(R.string.app_name),
            getString(R.string.location_tracking)
        )
        val notification = getNotificationBuilder(this, channelId).build()
        startForeground(1, notification)
    }

    // Initiate the request to track the device's location
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 500.toFloat() //if user traveled 500m
        }
        client = LocationServices.getFusedLocationProviderClient(this)
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            val location = result?.lastLocation
            location?.let {
                val latLng = "${location.latitude},${location.longitude}"
                logi("requestLocationUpdates: onLocationResult:: location = $latLng")
                val intent = Intent(LOCATION_UPDATES)
                intent.putExtra(LOCATION_EXTRA, latLng)
                sendBroadcast(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}