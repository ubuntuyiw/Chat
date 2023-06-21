package com.ubuntuyouiwe.chat.domain.model.chatgpt.request


data class OpenAIRequest(
    val messages: List<ChatGptMessage>,
)
