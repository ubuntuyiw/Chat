package com.ubuntuyouiwe.chat.data.dto.chatgpt

import kotlinx.serialization.Serializable

@Serializable
data class ChoiceDto(
    val chatGptMessageDto: ChatGptMessageDto,
    val finish_reason: String
)
