package com.ubuntuyouiwe.chat.presentation.chat

data class ChatGptState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false
)
