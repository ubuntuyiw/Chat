package com.ubuntuyouiwe.chat.data.source.remote.firebase.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.R
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.presentation.activity.MainActivity
import com.ubuntuyouiwe.chat.util.notification_channel.ConstantChannels.CHAT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class NotificationService() : FirebaseMessagingService() {
    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var notificationAdmin: NotificationManager

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("tokenss", "first" + token)

        serviceScope.launch {
            notificationRepository.saveTokenToDatabase(token)
        }

    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationStart(baseContext,message)
        Log.v("tokenss", "first" + message)

    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    override fun getStartCommandIntent(originalIntent: Intent?): Intent {
        val asd = RemoteInput.getResultsFromIntent(originalIntent!!)
        if (asd != null) {
            val replyText = asd.getCharSequence("key_text_reply")
            Log.v("tokenss", replyText.toString())
        }
        return super.getStartCommandIntent(originalIntent)

    }
    private fun notificationStart(baseContext: Context, remoteMessage: RemoteMessage) {


        val replyIntent = Intent(baseContext, NotificationReplyReceiver::class.java)

        val replyPendingIntent = PendingIntent.getBroadcast(baseContext, 0, replyIntent,
            PendingIntent.FLAG_MUTABLE)

        val person =  Person.Builder()
            .setName(remoteMessage.data.toString())
            .setImportant(true)
            .build()

        val message = remoteMessage.data.toString()


        val notificationMessage = NotificationCompat.MessagingStyle.Message(
            message,
            System.currentTimeMillis(),
            person
        )

        val style = NotificationCompat.MessagingStyle(person)
            .addMessage(notificationMessage)
            .addHistoricMessage(notificationMessage)



        val remoteInput = RemoteInput.Builder("key_text_reply")
            .setLabel("Cevapla")
            .build()

        val replyAction = NotificationCompat.Action.Builder(
            R.drawable.baseline_access_time_24,
            "Cevapla",
            replyPendingIntent
        ).addRemoteInput(remoteInput)
            .build()


        val builder = NotificationCompat.Builder(baseContext, CHAT_ID)
            .setSmallIcon(R.drawable.baseline_camera_24)
        builder.addAction(replyAction)
            .setStyle(style)
            .addAction(replyAction)
            .setCategory(Notification.CATEGORY_MESSAGE)

        notificationAdmin.notify(0, builder.build())
    }
}


