package com.ubuntuyouiwe.chat.data.dto.chatgpt.request

import kotlinx.serialization.Serializable


@Serializable
data class OpenAIRequestDto(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ChatGptMessageDto>,
    val max_tokens: Int = 500,
    val temperature: Double = 0.5,
    val top_p: Double = 0.9

)