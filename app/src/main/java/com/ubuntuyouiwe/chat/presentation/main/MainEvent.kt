package com.ubuntuyouiwe.chat.presentation.main

sealed class MainEvent {
    class CreateRoomChat(val roomName: String): MainEvent()
}
