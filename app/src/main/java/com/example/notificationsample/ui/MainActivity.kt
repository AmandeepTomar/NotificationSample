package com.example.notificationsample.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsample.CHANNEL_ID
import com.example.notificationsample.CHANNEL_ID2
import com.example.notificationsample.GROUP_ID_PERSONAL
import com.example.notificationsample.databinding.ActivityMainBinding
import com.example.notificationsample.notification.custom.CustomNotificationUtils
import com.example.notificationsample.notification.NotificationDefaultStyles
import com.example.notificationsample.notification.NotificationUtils
import com.example.notificationsample.notification.group.GroupNotificationUtils
import com.example.notificationsample.notification.message.Message
import com.example.notificationsample.notification.message.MessageNotificationHelper

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.canonicalName
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManagerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!this::notificationManager.isInitialized)
            notificationManager = NotificationManagerCompat.from(this)

        MESSAGES.add(Message("Hello","Naveen"))
        MESSAGES.add(Message("Hello All","Jacks"))
        MESSAGES.add(Message("How Are you , All",null))

        handleClicksOnViews()

    }

    private fun handleClicksOnViews() {
        if (this::binding.isInitialized)
            binding.apply {

                callNormalNotificationWithPendingIntents()
                callNotificationWithStyle()
                deleteChannelAndGroup()
                handleCustomNotifications()

                btnMessageNotification.setOnClickListener {
                    MessageNotificationHelper.sendMessageNotification(this@MainActivity)
                }

                btnProgressNotification.setOnClickListener {
                    NotificationUtils.sendDownloadNotification(this@MainActivity,notificationManager)
                }

                btnGroupNotification.setOnClickListener {
                    GroupNotificationUtils.sendGroupNotification(this@MainActivity,notificationManager)
                }

                btnGroupOldApiSupport.setOnClickListener {
                    GroupNotificationUtils.sendGroupNotificationForAndroid24(this@MainActivity,notificationManager)
                }


            }


    }

    private fun handleCustomNotifications() {
        if (!this::binding.isInitialized)
            return
        binding.apply {
            btnCustomNotification.setOnClickListener {
                CustomNotificationUtils.createCustomNotification(CHANNEL_ID2,this@MainActivity, notificationManager = notificationManager)
            }
        }
    }

    private fun callNormalNotificationWithPendingIntents() {
        if (!this::binding.isInitialized)
            return
        binding.apply {
            btnChannel1.setOnClickListener {
                if (!notificationManager.areNotificationsEnabled()) {
                    NotificationUtils.openNotificationSettings(this@MainActivity)
                    return@setOnClickListener
                }
                NotificationUtils.sendSimpleNotification(
                    this@MainActivity,
                    notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID,
                    notificationID = 1
                )
            }

            btnChannel2.setOnClickListener {
                if (NotificationUtils.isChannelBlocked(CHANNEL_ID2, this@MainActivity)) {
                    NotificationUtils.openChannelSettings(CHANNEL_ID2, this@MainActivity)
                    return@setOnClickListener
                }
                NotificationUtils.sendSimpleNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    2,
                    10000L
                )

            }

            btnPendingActivity.setOnClickListener {
                NotificationUtils.sendPendingIntentActivityNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID,
                    NotificationCompat.PRIORITY_HIGH,
                    3
                )
            }

            btnPendingBroadcast.setOnClickListener {
                NotificationUtils.sendPendingIntentBroadcastNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    4,
                )
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteChannelAndGroup() {
        if (!this::binding.isInitialized)
            return
        binding.apply {
            btnDeleteChannel.setOnClickListener {
                NotificationUtils.deleteNotificationChannel(CHANNEL_ID2,this@MainActivity)
            }

            btnDeleteGroup.setOnClickListener {
                NotificationUtils.deleteNotificationGroup(GROUP_ID_PERSONAL,this@MainActivity)
            }
        }
    }


    private fun callNotificationWithStyle() {
        if (!this::binding.isInitialized)
            return
        binding.apply {

            btnBigTextStyle.setOnClickListener {
                NotificationDefaultStyles.sendBigStyleNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    5,
                )
            }

            btnInboxStyle.setOnClickListener {
                NotificationDefaultStyles.sendInboxStyleNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    6,
                )
            }

            btnBigImage.setOnClickListener {
                NotificationDefaultStyles.sendBigImageNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    6,
                )
            }

            btnMediaNotification.setOnClickListener {
                //todo this one is pending from our side will update soon
                NotificationDefaultStyles.sendMediaNotification(
                    this@MainActivity, notificationManager,
                    getTextFromExitText(),
                    CHANNEL_ID2,
                    NotificationCompat.PRIORITY_HIGH,
                    6,
                )
            }
        }

    }

    private fun getTextFromExitText(): Pair<String, String>? {
        if (this::binding.isInitialized)
            binding.apply {
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                return Pair(title, description)
            }
        return null
    }


    companion object{
        var MESSAGES = mutableListOf<Message>()

    }


}