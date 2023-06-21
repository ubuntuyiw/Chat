package com.ubuntuyouiwe.chat.presentation.main

data class ChatRoomState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false
)
