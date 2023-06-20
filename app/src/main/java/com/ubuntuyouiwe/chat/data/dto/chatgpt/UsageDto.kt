package com.ubuntuyouiwe.chat.data.dto.chatgpt

import kotlinx.serialization.Serializable

@Serializable
data class UsageDto(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)