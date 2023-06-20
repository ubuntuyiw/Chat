package com.ubuntuyouiwe.chat.data.dto.chatgpt

import kotlinx.serialization.Serializable


@Serializable
data class OpenAIRequestDto(
    val model: String,
    val chatGptMessageDtos: List<ChatGptMessageDto>
)