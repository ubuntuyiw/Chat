package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.WhereEqualTo
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
): NotificationRepository {
    init {
        isNotificationDelegationEnabled(false)

    }

    override suspend fun createDeviceToken(): String? {
        return firebaseDataSource.firebaseMessaging().token.await()
    }
    override fun isNotificationDelegationEnabled(boolean: Boolean) {
        firebaseDataSource.firebaseMessaging().isNotificationDelegationEnabled = boolean
    }

    override suspend fun sendNotification(message: RemoteMessage) {
        firebaseDataSource.firebaseMessaging().send(message)
    }

    override suspend fun saveTokenToDatabase(onNewToken: String) {
        val documentId = firebaseDataSource.whereEqualToDocument(
            WhereEqualTo("email", firebaseDataSource.user()?.email),
            FirebaseCollection.Users
        ).documents.firstOrNull()?.id


        documentId?.let { id ->
            firebaseDataSource.update(
                FirebaseCollection.Users,
                documentId = id,
                data = hashMapOf("deviceToken" to onNewToken)
                )
        }

    }



}