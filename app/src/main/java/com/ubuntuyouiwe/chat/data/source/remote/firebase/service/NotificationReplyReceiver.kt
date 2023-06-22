package com.ubuntuyouiwe.chat.data.source.remote.firebase.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReplyReceiver : BroadcastReceiver() {
    private var job: Job? = null

    @Inject
    lateinit var messagingRepository: MessagingRepository
    @Inject
    lateinit var authRepository: AuthRepository
    override fun onReceive(context: Context, intent: Intent) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            val remoteInput = RemoteInput.getResultsFromIntent(intent)
            if (remoteInput != null) {
                val replyText = remoteInput.getCharSequence("key_text_reply")

                messagingRepository.insertMessage(
                    MessageResult(
                        email = authRepository.user()?.email,
                        message = replyText.toString()

                    )
                )
            }
        }

        job?.invokeOnCompletion {
            NotificationManagerCompat.from(context).cancel(0)
        }




    }

}
