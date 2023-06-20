package com.ubuntuyouiwe.chat.util.notification_channel

import android.app.NotificationChannel
import android.app.NotificationManager

object NotificationChannelCreator {


    fun createChannel(notificationAdmin: NotificationManager) {
        val channelId = "chat"
        val channelName = "Chat"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH
        var channel: NotificationChannel? = notificationAdmin.getNotificationChannel(channelId)




        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            notificationAdmin.createNotificationChannel(channel)
        }
    }
}