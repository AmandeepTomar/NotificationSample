package com.example.notificationsample.notification.group

import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.CHANNEL_ID2
import com.example.notificationsample.R
import com.example.notificationsample.notification.NotificationUtils

object GroupNotificationUtils {

    fun sendGroupNotification(context: Context,notificationManagerCompat: NotificationManagerCompat){

        // for Android 7 and above it is works fine as it get 5 notification it will add them in group
        // But this will not works as expected in android 24. and lower.
        val notification = NotificationCompat.Builder(context, CHANNEL_ID2)
            .setColor(Color.RED)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setContentText("Content Text is Here")
            .setContentTitle("Content Title")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)

        var i=0
        while (i<5){
            i++
            SystemClock.sleep(2000)
            notificationManagerCompat.notify(i,notification.build())
        }

    }

    // this will support in both android 21 and lower and 24 plus.
    // for lower version we have used inbox type style.
    fun sendGroupNotificationForAndroid24(context: Context,notificationManagerCompat: NotificationManagerCompat){

        // for Android 7 and above it is works fine as it get 5 notification it will add them in group
        // But this will not works as expected in android 24. and lower.
        val notification1 = NotificationCompat.Builder(context, CHANNEL_ID2)
            .setColor(Color.RED)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setContentText("Content Text is Here1")
            .setContentTitle("Content Title1")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup("example_group")
            .setAutoCancel(true)


        val notification2 = NotificationCompat.Builder(context, CHANNEL_ID2)

            .setColor(Color.RED)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setContentText("Content Text is Here1")
            .setContentTitle("Content Title1")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup("example_group")
            .setAutoCancel(true)


        val summeryNotification = NotificationCompat.Builder(context, CHANNEL_ID2)
            .setColor(Color.RED)
            .setSmallIcon(R.drawable.ic_send)
            .setContentText("Content Text is Here1")
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Content Title1 Content Text is Here1")
                .addLine("Content Title2 Content Text is Here2")
                .setBigContentTitle("2 Notification")
                .setSummaryText("user.gmail.com")
            )
            .setContentTitle("Content Title1")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setGroup("example_group")
            .setAutoCancel(true)
            .setGroupSummary(true)

        SystemClock.sleep(2000)
        notificationManagerCompat.notify(12,notification1.build())
        SystemClock.sleep(2000)
        notificationManagerCompat.notify(13,notification2.build())
        SystemClock.sleep(2000)
        notificationManagerCompat.notify(14,summeryNotification.build())

    }
}