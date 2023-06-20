package com.ubuntuyouiwe.chat.data.dto.chatgpt

import kotlinx.serialization.Serializable

@Serializable
data class ChatGptMessageDto(
    val role: String,
    val content: String
)
