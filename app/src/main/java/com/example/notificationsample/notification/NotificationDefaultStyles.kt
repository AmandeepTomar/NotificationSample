package com.example.notificationsample.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.BROADCAST_DATA
import com.example.notificationsample.R

object NotificationDefaultStyles {

    fun sendBigStyleNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val broadcastIntent = Intent(context,NotificationReceiver::class.java).apply {
                putExtra(BROADCAST_DATA,pair.second.plus(channelId).plus("/r $notificationID"))
            }

            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0,broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.splash_logo)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(NotificationUtils.getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setLargeIcon(largeIcon)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                    .setBigContentTitle("Big Content Title")
                    .setSummaryText("Summery Big")
                    .bigText(context.getString(R.string.txt_big_textSample)))
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_launcher_foreground,"Toast",pendingIntent)

            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(NotificationUtils.TAG, "sendNotification: Pair is null")
        }

    }

    fun sendInboxStyleNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val broadcastIntent = Intent(context,NotificationReceiver::class.java).apply {
                putExtra(BROADCAST_DATA,pair.second.plus(channelId).plus("/r $notificationID"))
            }

            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0,broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.splash_logo)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(NotificationUtils.getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setLargeIcon(largeIcon)
                .setStyle(
                    NotificationCompat.InboxStyle()
                    .setBigContentTitle("Inbox Content Title")
                    .setSummaryText("Summery Inbox")
                    .addLine("Line no 1")
                    .addLine("Line no 2")
                    .addLine("Line no 3")
                    .addLine("Line no 4")
                    .addLine("Line no 5")
                    .addLine("Line no 6")
                    .addLine("Line no 7")
                    .addLine("Line no 8 ")
                )
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_launcher_foreground,"Toast",pendingIntent)

            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(NotificationUtils.TAG, "sendNotification: Pair is null")
        }
    }


    fun sendBigImageNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val picture = BitmapFactory.decodeResource(context.resources, R.drawable.big_image)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(NotificationUtils.getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setLargeIcon(picture)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                    .bigPicture(picture)
                    .bigLargeIcon(null))
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)

            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(NotificationUtils.TAG, "sendNotification: Pair is null")
        }
    }

    fun sendMediaNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val picture = BitmapFactory.decodeResource(context.resources, R.drawable.big_image)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(NotificationUtils.getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setLargeIcon(picture)
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)

            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(NotificationUtils.TAG, "sendNotification: Pair is null")
        }
    }

}