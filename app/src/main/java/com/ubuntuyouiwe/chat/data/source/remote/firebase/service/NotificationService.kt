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

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationStart(baseContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }


    fun notificationStart(baseContext: Context) {

        val builder: NotificationCompat.Builder





        val snoozeIntent = Intent(baseContext, MainActivity::class.java)
        snoozeIntent.action = "ACTION_SNOOZE"
        snoozeIntent.putExtra(Notification.EXTRA_NOTIFICATION_ID, 0)


        val message = NotificationCompat.MessagingStyle.Message(
            "Merhaba, nasılsın?",
            System.currentTimeMillis(),
            Person.Builder()
                .setName("İrem")
                .build()
        )


        val textInput = Intent(baseContext, MainActivity::class.java)
        val chooserIntent = Intent.createChooser(textInput, "Cevapla")
        val chooserPendingIntent = PendingIntent.getBroadcast(baseContext, 0, chooserIntent, PendingIntent.FLAG_UPDATE_CURRENT  or PendingIntent.FLAG_MUTABLE)



        val remoteInput = RemoteInput.Builder("key_text_reply")
            .setLabel("Cevapla")
            .build()

        val replyAction = NotificationCompat.Action.Builder(
            R.drawable.baseline_access_time_24,
            "Cevapla",
            chooserPendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        val person = Person.Builder()
            .setName("dwa")
            .build()


        val channelId = "Kanalid"
        val channelName = "Kanal Adı"
        val channelPromotion = "Kanal tanıtım"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH
        var channel: NotificationChannel? = notificationAdmin.getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelPromotion
            notificationAdmin.createNotificationChannel(channel)
        }
        builder = NotificationCompat.Builder(baseContext, channelId).setSmallIcon(R.drawable.baseline_camera_24)
        builder.addAction(replyAction).addAction(replyAction)
            .setStyle(NotificationCompat.MessagingStyle(person)
                .addMessage(message))
            .setCategory(Notification.CATEGORY_MESSAGE)

        val uuid:Int= Random.nextInt()
        notificationAdmin.notify(uuid, builder.build())
    }
}


