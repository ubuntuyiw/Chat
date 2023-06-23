package com.ubuntuyouiwe.chat.data.source.remote.firebase.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationStart(baseContext, message)
        Log.v("tokenss", "first" + message)

    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun notificationStart(baseContext: Context, remoteMessage: RemoteMessage) {

        val replyIntent = Intent(baseContext, NotificationReplyReceiver::class.java)

        val replyPendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, replyIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )





        val target = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val bubbleIntent = PendingIntent.getActivity(baseContext, 0, target, PendingIntent.FLAG_MUTABLE)


        val bubbleData = Notification.BubbleMetadata.Builder(bubbleIntent,
            Icon.createWithResource(baseContext, R.drawable.baseline_access_time_24))
            .setDesiredHeight(600)
            .setIntent(bubbleIntent)
            .setAutoExpandBubble(true)
            .setSuppressNotification(true)
            .build()


        val person = Person.Builder()
            .setName("Ahmet")
            .setImportant(true)
            .build()

        val message = remoteMessage.data.toString()

        val notificationMessage = Notification.MessagingStyle.Message(
            "Message Text",
            System.currentTimeMillis(),
            person,  // Gönderici bilgisi
        )

        val style = Notification.MessagingStyle(person)
            .addMessage(notificationMessage)

        /*val remoteInput = RemoteInput.Builder("key_text_reply")
            .setLabel("Cevapla")
            .build()*/

        /*val replyAction = NotificationCompat.Action.Builder(
            R.drawable.baseline_access_time_24,
            "Cevapla",
            replyPendingIntent
        ).addRemoteInput(remoteInput)
            .build()*/

        val shortcut ="ad"
            ShortcutInfo.Builder(baseContext, shortcut)
                .setCategories(setOf(Notification.CATEGORY_MESSAGE))
                .setIntent(Intent(Intent.ACTION_DEFAULT))
                .setLongLived(true)
                .setShortLabel(person.name!!)
                .build()


        Notification.Builder(baseContext, CHAT_ID).apply {
            setContentTitle("My notification")
            setContentText("Hello World!")
            setContentIntent(bubbleIntent)
            setSmallIcon(R.drawable.baseline_camera_24)
            setAutoCancel(true)
            setBubbleMetadata(bubbleData)
            setShortcutId(shortcut)
            setStyle(style)
            //addAction(replyAction)

            notificationAdmin.notify(0, this.build())
        }
    }
}


