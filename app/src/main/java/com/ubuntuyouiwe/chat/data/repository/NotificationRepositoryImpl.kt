package com.ubuntuyouiwe.chat.data.repository

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.R
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.receiver.NotificationReplyReceiver
import com.ubuntuyouiwe.chat.data.util.DatabaseFieldNames
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.WhereEqualTo
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.presentation.activity.MainActivity
import com.ubuntuyouiwe.chat.util.notification_channel.Constants.KEY_TEXT_REPLY
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelInfo
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationOnEvent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val notificationAdmin: NotificationManager
) : NotificationRepository {

    override var messages: ArrayList<Notification.MessagingStyle.Message> = ArrayList()


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
            WhereEqualTo(DatabaseFieldNames.EMAIL, firebaseDataSource.user()?.email),
            FirebaseCollection.Users
        ).documents.firstOrNull()?.id


        documentId?.let { id ->
            firebaseDataSource.update(
                FirebaseCollection.Users,
                documentId = id,
                data = hashMapOf(DatabaseFieldNames.DEVICE_TOKEN.fieldNames to onNewToken)
            )
        }

    }

    private fun intent(baseContext: Context, actionName: String): Intent {
        return Intent(baseContext, NotificationReplyReceiver::class.java).apply {
            action = actionName
            putExtra("1", 0)
        }
    }

    override fun pendingIntent(baseContext: Context, actionName: String): PendingIntent {



        return PendingIntent.getBroadcast(
            baseContext,
            0,
            intent(baseContext, actionName),
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun notificationPerson(remoteMessage: RemoteMessage?, error: Boolean): Person {
        val owner = remoteMessage?.let {
            val email = it.data["email"].toString()
            email.substringBefore('@')

        }?: run {
            if (error) "Error!!" else "You"
        }
        return Person.Builder()
            .setName(owner)
            .setImportant(true)
            .build()
    }

    override fun notificationMessage(
        remoteMessage: RemoteMessage?,
        localMessage: String?,
        person: Person
    ): Notification.MessagingStyle.Message {
        val message = remoteMessage?.let {
            it.data["message"].toString()
        }?: run {
            localMessage
        }
        return Notification.MessagingStyle.Message(
            message!!,
            System.currentTimeMillis(),
            person,
        )
    }

    override fun remoteInput(
        replyPendingIntent: PendingIntent,
        baseContext: Context
    ): Notification.Action.Builder {
        val replyLabel: String = "Answer"
        val label: String = "Type a message"

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(label)
            build()
        }

        return Notification.Action.Builder(
            Icon.createWithResource(baseContext, R.drawable.baseline_send_24),
            replyLabel,
            replyPendingIntent
        ).addRemoteInput(remoteInput).setAllowGeneratedReplies(true)
    }

    override fun notification(
        baseContext: Context,
        style: Notification.MessagingStyle,
        action: Notification.Action,
        contentIntent: PendingIntent,
        deleteIntent: PendingIntent,
        channel: NotificationChannelInfo
    ) {
        Notification.Builder(baseContext, channel.id).apply {
            setSmallIcon(R.drawable.chat_logo)
            setStyle(style)
            addAction(action)
            setContentIntent(contentIntent)
            setDeleteIntent(deleteIntent)
            setAutoCancel(true)
            setCategory(Notification.CATEGORY_MESSAGE)
            notificationAdmin.notify(1, build())
        }
    }
    override fun sendStyledNotification(
        baseContext: Context,
        remoteMessage: RemoteMessage?,
        localMessage: String?,
        error: Boolean,
        channel: NotificationChannelInfo
    ) {
        val actionIntent = pendingIntent(baseContext, NotificationOnEvent.SEND.actionName)
        val deleteIntent = pendingIntent(baseContext, NotificationOnEvent.DELETE.actionName)

        val intent = Intent(baseContext, MainActivity::class.java).apply {
            putExtra(NotificationOnEvent.OPEN.actionName, NotificationOnEvent.OPEN.actionName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val openNotifyIntent =  PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val person = notificationPerson(remoteMessage,error)

        val notificationMessage = notificationMessage(remoteMessage, localMessage, person)

        val style = Notification.MessagingStyle(person)

        for (i in messages) {
            style.addMessage(i)
        }
        style.conversationTitle = channel.conversationTitle

        style.addMessage(notificationMessage)
        messages.add(notificationMessage)


        val action = remoteInput(actionIntent, baseContext).build()

        notification(baseContext, style, action, openNotifyIntent, deleteIntent, channel)
    }


}