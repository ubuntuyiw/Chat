package com.ubuntuyouiwe.chat.util.notification_channel

enum class NotificationChannelInfo(
    val id: String,
    val channelName: String,
    val conversationTitle: String
) {
    CHAT_ID("com.ubuntuyouiwe.chat.CHAT_ID", "Chat", "Chat Group")
}