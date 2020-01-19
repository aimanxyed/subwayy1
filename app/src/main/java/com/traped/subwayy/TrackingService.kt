package com.traped.subwayy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class TrackingService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {

            Log.d("LOCATION UPDATE", "Onlocation result called")
            locationResult ?: return
            for (location in locationResult.locations) {
                Log.d("LOCATION UPDATE", "Onlocation result in loop")
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    Log.d("LOCATION UPDATE", "Location received $latLng")
                    TrackingData.locationCallback.invoke(latLng)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest =
                LocationRequest().setInterval(LOCATION_INTERVAL_IN_MILLISECONDS)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        startLocationUpdates()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showTrackingNotification()
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopLocationUpdates()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    private fun startLocationUpdates() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            it.result?.let {
                TrackingData.locationCallback.invoke(LatLng(it.latitude, it.longitude))
            }
        }
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun showTrackingNotification() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        val pendingIntent = PendingIntent.getActivity(
                this,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )

        createSilentChannelNotification()

        val notification =
                NotificationCompat.Builder(this, ID_SILENT_NOTIFICATIONS).apply {
                    setContentTitle("Subwayy")
                    setSmallIcon(R.mipmap.ic_launcher)
                    priority = NotificationCompat.PRIORITY_LOW
                    setContentIntent(pendingIntent)
                }.build()

        startForeground(INT_NOTIFICATION_ID, notification)
    }

    fun createSilentChannelNotification() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Channel for Silent Group Notifications
        notificationManager.createNotificationChannel(
                NotificationChannel(
                        ID_SILENT_NOTIFICATIONS,
                        NAME_SILENT_NOTIFICATIONS,
                        NotificationManager.IMPORTANCE_LOW
                )
        )

    }

    companion object {
        const val LOCATION_INTERVAL_IN_MILLISECONDS = 4000L
        private const val INT_NOTIFICATION_ID = 99999

        const val ID_SILENT_NOTIFICATIONS = "0"
        const val NAME_SILENT_NOTIFICATIONS = "silent"
    }
}
