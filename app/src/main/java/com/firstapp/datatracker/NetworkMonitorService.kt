package com.firstapp.datatracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dev.jahidhasanco.networkusage.Interval
import dev.jahidhasanco.networkusage.NetworkType
import dev.jahidhasanco.networkusage.NetworkUsageManager
import dev.jahidhasanco.networkusage.Util

class NetworkMonitorService : Service() {
    private val CHANNEL_ID = "network_monitor_channel"
    private val NOTIFICATION_ID = 1
    private val CHANNEL_NAME = "Network Usage Channel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        checkInternetUsageInBackground()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun checkInternetUsageInBackground() {
        Thread {
            while (true) {
                Thread.sleep(10000) // اندازه‌گیری مصرف اینترنت هر 5 ثانیه یکبار (به عنوان مثال)
                if (!ProfilePreferences.getNotification()) {
                    if (isConnectedToInternet()) {
                        val mobileDataUsage = getMobileDataUsage()
                        if (mobileDataUsage >= ProfilePreferences.getLimit()) {
                            sendNotification("مصرف اینترنت به حد مشخص رسید!")
                            ProfilePreferences.setNotification(true)
                        }
                    }
                }
            }
        }.start()
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    private fun getMobileDataUsage(): Long {
        val networkUsage = NetworkUsageManager(this, Util.getSubscriberId(this))
        val todayM = networkUsage.getUsage(Interval.today, NetworkType.MOBILE)
        return todayM.downloads + todayM.uploads
    }

    private fun sendNotification(message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("مصرف اینترنت موبایل")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)

    }
}
