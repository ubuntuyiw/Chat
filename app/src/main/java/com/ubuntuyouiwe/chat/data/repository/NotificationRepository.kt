package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {


    private suspend fun createDeviceToken(): String? {
        return firebaseDataSource.firebaseMessaging().token.await()
    }
    private suspend fun isNotificationDelegationEnabled(boolean: Boolean) {
        firebaseDataSource.firebaseMessaging().isNotificationDelegationEnabled = boolean
    }

    private suspend fun sendNotification(message: RemoteMessage) {
        firebaseDataSource.firebaseMessaging().send(message)
    }



}