package com.example.notificationsample.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.BROADCAST_DATA
import com.example.notificationsample.CHANNEL_ID
import com.example.notificationsample.CHANNEL_ID2
import com.example.notificationsample.R
import com.example.notificationsample.ui.MainActivity

object NotificationUtils {
    val TAG: String = NotificationUtils::class.java.canonicalName

    fun sendSimpleNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L

    ) {
        textFromExitText?.let { pair ->
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)


            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(TAG, "sendNotification: Pair is null")
        }

    }


    fun sendPendingIntentActivityNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setContentIntent(pendingIntent)


            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(TAG, "sendNotification: Pair is null")
        }

    }


    fun sendPendingIntentBroadcastNotification(
        context: Context,
        notificationManager: NotificationManagerCompat,
        textFromExitText: Pair<String, String>?,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        notificationID: Int,
        timeout: Long = 100000L
    ) {

        textFromExitText?.let { pair ->
            val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra(BROADCAST_DATA, pair.second.plus(channelId).plus("/r $notificationID"))
            }

            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(getNotificationIcon(channelId))
                .setContentTitle(pair.first.plus(channelId))
                .setContentText(pair.second.plus(channelId))
                .setPriority(priority)
                .setAutoCancel(true)
                .setTimeoutAfter(timeout)
                .setColor(Color.GREEN)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_launcher_foreground, "Toast", pendingIntent)


            notificationManager.notify(notificationID, builder.build())
        } ?: kotlin.run {
            Log.e(TAG, "sendNotification: Pair is null")
        }

    }


    fun sendDownloadNotification(context: Context, notificationManager: NotificationManagerCompat) {
        var maxProgress = 100;
        val notification = NotificationCompat.Builder(context, CHANNEL_ID2)
            .setSmallIcon(R.drawable.ic_two_notification_icon)
            .setColor(Color.GREEN)
            .setContentTitle("Download")
            .setContentText("Download is in Progress")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setProgress(maxProgress, 0, false)

        notificationManager.notify(12, notification.build())

        // here we update the Progress bar pon background thread

        Thread {
            run {
                SystemClock.sleep(2000)
                var i = 0
                while (i < 100) {
                    i = i + 10
                    notification.setProgress(maxProgress, i, false)
                    notificationManager.notify(12, notification.build())
                    SystemClock.sleep(1000)
                }
                notification.setContentText("Download finished!!")
                notification.setProgress(0, 0, false)
                notification.setOngoing(false)
                notificationManager.notify(12, notification.build())
            }

        }.start()

    }

    /***
     * this methiod is used to enable or redirect the aap to notification setting screen
     * if complete notification is disabled.
     * for Android O and above we will land or open the notification setting
     * for Android Lower O we will open the app setting page and enable / disable notification.
     * */
    fun openNotificationSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            context.startActivity(intent)
        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${context.packageName}")
            context.startActivity(intent)
        }
    }


    /***
     * This method is used for O and above where we can block any channel and group notification.
     * We have more control on blocking and enable notification.
     */

    @RequiresApi(26)
    fun isChannelBlocked(channelId: String, context: Context): Boolean {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notificationChannel = notificationManager.getNotificationChannel(channelId)

        return ((notificationChannel != null) && notificationChannel.importance == NotificationManager.IMPORTANCE_NONE)
    }

    @RequiresApi(26)
    fun openChannelSettings(channelId: String, context: Context) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
        context.startActivity(intent)
    }

    /***
     * deleting channel is not a good idea, When we delete the channel it will show user on setting screen that channel has been deleted.
     * So we need to update the same channel.
     * We will write complete dynamic code.
     * When you delete the channel and change some settings prior delete, When we re launch the app the same setting is shown ion deleted channel that you selected previously.
     * */
    @RequiresApi(26)
    fun deleteNotificationChannel(channelId: String, context: Context) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.deleteNotificationChannel(channelId)
    }
    @RequiresApi(26)
    fun deleteNotificationGroup(groupId:String,context: Context){
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.deleteNotificationChannelGroup(groupId)
    }

    fun getNotificationIcon(channelId: String): Int {
        return if (channelId == CHANNEL_ID) {
            R.drawable.ic_one_notification_icon
        } else {
            R.drawable.ic_two_notification_icon
        }

    }
}