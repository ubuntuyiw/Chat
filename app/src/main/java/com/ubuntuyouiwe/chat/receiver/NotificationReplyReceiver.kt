package com.ubuntuyouiwe.chat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.util.notification_channel.Constants.KEY_TEXT_REPLY
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelInfo
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationOnEvent
import com.ubuntuyouiwe.chat.worker.MessageWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReplyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationRepository: NotificationRepository
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NotificationOnEvent.SEND.actionName -> {
                val remoteInput = RemoteInput.getResultsFromIntent(intent)
                remoteInput?.let { bundle ->

                    val replyText = bundle.getCharSequence(KEY_TEXT_REPLY)
                    if (replyText?.isBlank() == true) {
                        notificationRepository.sendStyledNotification(
                            context,
                            remoteMessage = null,
                            localMessage = "Blank message is not sent",
                            true,
                            channel = NotificationChannelInfo.CHAT_ID,
                            true

                        )
                        return
                    }
                    val inputData = workDataOf(KEY_TEXT_REPLY to replyText.toString())
                    val messageWorkRequest = OneTimeWorkRequestBuilder<MessageWorker>()
                        .setInputData(inputData)
                        .build()

                    WorkManager.getInstance(context).enqueue(messageWorkRequest)
                }

            }

            NotificationOnEvent.DELETE.actionName -> {
                notificationRepository.messages.clear()
            }

            NotificationOnEvent.OPEN.actionName -> {
                notificationRepository.messages.clear()
            }
        }


    }


}
