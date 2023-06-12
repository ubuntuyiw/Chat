package com.ubuntuyouiwe.chat.domain.model

data class Messages(
    val messageResult: List<MessageResult>?,
    val isFromCache: Boolean?
)
