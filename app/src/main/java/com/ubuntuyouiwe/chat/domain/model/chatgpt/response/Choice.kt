package com.ubuntuyouiwe.chat.domain.model.chatgpt.response

import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.ChatGptMessageDto

data class Choice(
    val message: ChatGptMessageDto,
)
