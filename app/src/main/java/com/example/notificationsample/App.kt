package com.example.notificationsample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationGroupWithChannel()
        createNotificationGroupWithChannel2()
        createNotificationGroupWithChannel3()
    }

    private fun createNotificationGroupWithChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationGroup = NotificationChannelGroup(GROUP_ID_PERSONAL,"Personal" )
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                group = GROUP_ID_PERSONAL
            }

            val channel2 = NotificationChannel(CHANNEL_ID2, name, importance).apply {
                description = descriptionText
                enableLights(true)
                group = GROUP_ID_PERSONAL
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(notificationGroup)
            notificationManager.createNotificationChannel(channel2)
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun createNotificationGroupWithChannel2() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationGroup = NotificationChannelGroup(GROUP_ID_PROFESSIONAL,"Professional" )

            val name = getString(R.string.channel_name_2)
            val descriptionText = getString(R.string.channel_description_2)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID3, name, importance).apply {
                description = descriptionText
                group = GROUP_ID_PROFESSIONAL

            }
            val channel2 = NotificationChannel(CHANNEL_ID4, name, importance).apply {
                description = descriptionText
                group = GROUP_ID_PROFESSIONAL

            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(notificationGroup)

            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun createNotificationGroupWithChannel3() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationGroup = NotificationChannelGroup(GROUP_ID_CUSTOM,"Custom" )

            val name = getString(R.string.channel_name_2)
            val descriptionText = getString(R.string.channel_description_2)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID5, name, importance).apply {
                description = descriptionText
                group = GROUP_ID_CUSTOM

            }
            val channel2 = NotificationChannel(CHANNEL_ID6, name, importance).apply {
                description = descriptionText
                group = GROUP_ID_CUSTOM

            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(notificationGroup)
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channel2)
        }
    }
}