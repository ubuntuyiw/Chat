package com.ubuntuyouiwe.chat.data.dto

data class MessagesDto(
    val messageResultDto: List<MessageResultDto>,
    val isFromCache: Boolean
)