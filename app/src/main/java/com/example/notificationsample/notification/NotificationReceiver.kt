package com.example.notificationsample.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.notificationsample.BROADCAST_DATA
import com.example.notificationsample.showToast

class NotificationReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.extras?.get(BROADCAST_DATA)
        if (message != null) {
            context?.showToast(message.toString())
        }
    }

}