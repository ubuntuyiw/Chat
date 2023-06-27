package com.ubuntuyouiwe.chat.domain.repository

import android.app.Notification
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelInfo

interface NotificationRepository {
    val messages: ArrayList<Notification.MessagingStyle.Message>
    suspend fun createDeviceToken(): String?


    suspend fun saveTokenToDatabase(onNewToken: String)

    fun pendingIntent(baseContext: Context, actionName: String): PendingIntent

    fun notificationPerson(remoteMessage: RemoteMessage?, error: Boolean): Person

    fun notificationMessage(
        remoteMessage: RemoteMessage? = null,
        localMessage: String? = null,
        person: Person
    ): Notification.MessagingStyle.Message


    fun remoteInput(
        replyPendingIntent: PendingIntent,
        baseContext: Context
    ): Notification.Action.Builder

    fun notification(
        baseContext: Context,
        style: Notification.MessagingStyle,
        action: Notification.Action,
        contentIntent: PendingIntent,
        deleteIntent: PendingIntent,
        channel: NotificationChannelInfo,
        setOnlyAlertOnce: Boolean
    )

    fun sendStyledNotification(
        baseContext: Context,
        remoteMessage: RemoteMessage?,
        localMessage: String?,
        error: Boolean,
        channel: NotificationChannelInfo,
        setOnlyAlertOnce: Boolean
    )
}