package com.ubuntuyouiwe.chat.util.notification_channel

import android.app.NotificationChannel
import android.app.NotificationManager

object NotificationChannelCreator {



    fun chatChannel(notificationAdmin: NotificationManager) {
        val channelId = NotificationChannelInfo.CHAT_ID.id
        val channelName = NotificationChannelInfo.CHAT_ID.channelName
        val channelPriority = NotificationManager.IMPORTANCE_HIGH
        var channel: NotificationChannel? = notificationAdmin.getNotificationChannel(channelId)

        channel = channel?: NotificationChannel(channelId, channelName, channelPriority)
        notificationAdmin.createNotificationChannel(channel)

    }
}