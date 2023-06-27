package com.ubuntuyouiwe.chat.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService() : FirebaseMessagingService() {
    @Inject
    lateinit var notificationRepository: NotificationRepository


    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        serviceScope.launch {
            notificationRepository.saveTokenToDatabase(token)
        }

    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationRepository.sendStyledNotification(
            baseContext,
            remoteMessage = message,
            localMessage = null,
            false,
            channel = NotificationChannelInfo.CHAT_ID,
            false
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }


}


