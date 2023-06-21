package com.ubuntuyouiwe.chat.data.dto.chatgpt.response

import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.ChatGptMessageDto
import kotlinx.serialization.Serializable

@Serializable
data class ChoiceDto(
    val message: ChatGptMessageDto,
    val finish_reason: String
)
