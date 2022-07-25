package com.example.notificationsample.notification.custom

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.BROADCAST_DATA
import com.example.notificationsample.BROADCAST_NOTIFICATION_ID
import com.example.notificationsample.R
import com.example.notificationsample.notification.custom.receiver.CustomNotificationReceiver
import kotlin.random.Random

object CustomNotificationUtils {

    fun createCustomNotification(channelId:String,context: Context,notificationManager: NotificationManagerCompat){

        val collapsedView = RemoteViews(context.packageName,R.layout.custom_notification_collapsed)
        val expandedView = RemoteViews(context.packageName,R.layout.custom_notification_expended)
        val notificationID = Random.nextInt()
        val intent = Intent(context,CustomNotificationReceiver::class.java).apply {
            putExtra(BROADCAST_DATA,"We have received on click event here")
            putExtra(BROADCAST_NOTIFICATION_ID,notificationID)
        }

        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)

        collapsedView.setTextViewText(R.id.tvCollapsedTitle,"Collapsed Title")
        collapsedView.setTextViewText(R.id.tvCollapsedDescription,"Collapsed Description")

        expandedView.setTextViewText(R.id.tvExpendedDescription,context.getString(R.string.txt_big_textSample))
        expandedView.setTextViewText(R.id.tvExpendedTitle,"Title of Expended View")

        expandedView.setImageViewResource(R.id.imageExpended,R.drawable.splash_logo)
        expandedView.setOnClickPendingIntent(R.id.imageExpended,pendingIntent)

        val notification = androidx.core.app.NotificationCompat.Builder(context,channelId)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setStyle(androidx.core.app.NotificationCompat.DecoratedCustomViewStyle())
            .setOnlyAlertOnce(true)

        notificationManager.notify(notificationID,notification.build())

    }
}