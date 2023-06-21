package com.ubuntuyouiwe.chat.data.dto.chatgpt.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenAIResponseDto(
    val id: String,
    @SerialName("object")
    val objectType: String,
    val created: Int,
    val model: String,
    val choices: List<ChoiceDto>,
    val usage: UsageDto

)
