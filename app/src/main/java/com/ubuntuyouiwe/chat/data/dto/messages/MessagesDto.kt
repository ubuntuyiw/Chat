package com.ubuntuyouiwe.chat.data.dto.messages

data class MessagesDto(
    val messageResultDto: List<MessageResultDto>,
    val isFromCache: Boolean
)