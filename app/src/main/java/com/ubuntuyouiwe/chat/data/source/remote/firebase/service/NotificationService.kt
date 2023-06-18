package com.ubuntuyouiwe.chat.data.source.remote.firebase.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService() : FirebaseMessagingService() {
    @Inject
    lateinit var firebase: AuthRepository

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("tokenss", "first" + token)


        val deviceToken = hashMapOf<String, Any?>("deviceToken" to token)


        firebase.listenUserOnlineStatus().onEach { user ->
            val documentId = firebase.getDocumentIdByEmail(
                user?.email,
                FirebaseCollection.Users
            )
            documentId?.let {
                firebase.update(
                    it,
                    deviceToken,
                    FirebaseCollection.Users
                )
            }
            Log.v("tokenss", "sec" + token)

        }.launchIn(serviceScope)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.v("tokenss", "geldi: " + message.notification?.title.toString())
        Log.v("tokenss", "message.notification?.title.toString()")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}