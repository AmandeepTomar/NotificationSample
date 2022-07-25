package com.example.notificationsample.notification.custom.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.BROADCAST_DATA
import com.example.notificationsample.BROADCAST_NOTIFICATION_ID
import com.example.notificationsample.showToast

class CustomNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = NotificationManagerCompat.from(context)
       val data = intent?.getStringExtra(BROADCAST_DATA)
        val id = intent?.getIntExtra(BROADCAST_NOTIFICATION_ID,-1)
        if (id != null) {
            notificationManager.cancel(id)
        }

        data?.let { context.showToast(it) }
    }
}