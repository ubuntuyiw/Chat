package com.ubuntuyouiwe.chat.util.notification_channel

import android.app.NotificationChannel
import android.app.NotificationManager
import com.ubuntuyouiwe.chat.util.notification_channel.ConstantChannels.CHAT_ID
import com.ubuntuyouiwe.chat.util.notification_channel.ConstantChannels.CHAT_NAME

object NotificationChannelCreator {



    fun chatChannel(notificationAdmin: NotificationManager) {
        val channelId = CHAT_ID
        val channelName = CHAT_NAME
        val channelPriority = NotificationManager.IMPORTANCE_HIGH
        var channel: NotificationChannel? = notificationAdmin.getNotificationChannel(channelId)


        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            notificationAdmin.createNotificationChannel(channel)
        }
    }
}