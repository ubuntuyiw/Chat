package com.ubuntuyouiwe.chat.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.util.notification_channel.Constants.KEY_TEXT_REPLY
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class MessageWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val messagingRepository: MessagingRepository,
    private val authRepository: AuthRepository
) : CoroutineWorker(appContext, workerParams) {


    @Inject
    lateinit var notificationRepository: NotificationRepository
    override suspend fun doWork(): Result {

        val replyText = inputData.getString(KEY_TEXT_REPLY) ?: return Result.failure()
        val userEmail = authRepository.user()?.email
        return if (userEmail != null) {
            try {
                val messageResult = MessageResult(
                    email = userEmail,
                    message = replyText
                )

                messagingRepository.insertMessage(messageResult)

                notificationRepository.sendStyledNotification(
                    appContext,
                    remoteMessage = null,
                    localMessage = replyText,
                    false,
                    channel = NotificationChannelInfo.CHAT_ID
                )
                Result.success()

            } catch (e: Exception) {
                Result.failure()
            }
        } else {
            Result.failure()
        }

    }
}
