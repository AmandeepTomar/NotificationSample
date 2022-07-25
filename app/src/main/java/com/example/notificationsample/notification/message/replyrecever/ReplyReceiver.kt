package com.example.notificationsample.notification.message.replyrecever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.example.notificationsample.KEY_TEXT_REPLY
import com.example.notificationsample.notification.message.Message
import com.example.notificationsample.notification.message.MessageNotificationHelper
import com.example.notificationsample.ui.MainActivity


class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput= intent?.let { RemoteInput.getResultsFromIntent(it) }

        if (remoteInput != null) {
            val replyText = remoteInput.getCharSequence(KEY_TEXT_REPLY)
            val answer = replyText?.let { Message(it, "Me") }
            if (answer != null) {
                MainActivity.MESSAGES.add(answer)
            }
            context?.let { MessageNotificationHelper.sendMessageNotification(it) }
        }
    }
}