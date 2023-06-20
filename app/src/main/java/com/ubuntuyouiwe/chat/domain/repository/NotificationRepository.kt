package com.ubuntuyouiwe.chat.domain.repository

import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.tasks.await

interface NotificationRepository {

    suspend fun createDeviceToken(): String?
    fun isNotificationDelegationEnabled(boolean: Boolean)

    suspend fun sendNotification(message: RemoteMessage)

    suspend fun saveTokenToDatabase(onNewToken: String)
}