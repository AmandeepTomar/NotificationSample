package com.example.notificationsample.notification.message

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.CHANNEL_ID2
import com.example.notificationsample.KEY_TEXT_REPLY
import com.example.notificationsample.R
import com.example.notificationsample.notification.message.replyrecever.ReplyReceiver
import com.example.notificationsample.ui.MainActivity


object MessageNotificationHelper {

    fun sendMessageNotification(context: Context) {

        // create intent for launching activity
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Pending intent for the Activity
        val pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0)

       // Remote Input for type and send
        val remoteInput = androidx.core.app.RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel("Type Your Reply...")
            .build()

        // Reply Broad cast here
        val replyIntent = Intent(context, ReplyReceiver::class.java)

        val replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0)

        val replyAction =
            NotificationCompat.Action.Builder(R.drawable.ic_send, "Reply", replyPendingIntent)
                .addRemoteInput(remoteInput).build()

        val messagingStyle = NotificationCompat.MessagingStyle("Me") // here we can use the Person in place of me.
        messagingStyle.conversationTitle = "Group Chat"

        for (messages in MainActivity.MESSAGES){
            val notificationMessage: NotificationCompat.MessagingStyle.Message =
                NotificationCompat.MessagingStyle.Message(
                    messages.text,
                    messages.timeStamp,
                    messages.sender
                )
            messagingStyle.addMessage(notificationMessage)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID2)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setStyle(messagingStyle)
            .addAction(replyAction)
            .setColor(Color.BLUE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(10, notification)

    }
}