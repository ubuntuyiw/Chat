package com.ubuntuyouiwe.chat.presentation.chat

sealed class ChatEvent(val message: String?) {
    class SendMessage(message: String) : ChatEvent(message)

    class LogOut() : ChatEvent(null)

}
