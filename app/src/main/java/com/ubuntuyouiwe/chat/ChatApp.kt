package com.ubuntuyouiwe.chat

import android.app.Application
import android.app.NotificationManager
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationChannelCreator
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ChatApp: Application() {
    @Inject
    lateinit var manager: NotificationManager
    override fun onCreate() {
        super.onCreate()
        NotificationChannelCreator.chatChannel(manager)
    }
}