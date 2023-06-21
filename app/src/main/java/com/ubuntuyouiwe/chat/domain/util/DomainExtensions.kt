package com.ubuntuyouiwe.chat.domain.util

import com.ubuntuyouiwe.chat.data.dto.ChatRoomDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.ChatGptMessageDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.OpenAIRequestDto
import com.ubuntuyouiwe.chat.domain.model.ChatRoom
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.model.chatgpt.request.ChatGptMessage
import com.ubuntuyouiwe.chat.domain.model.chatgpt.request.OpenAIRequest

fun MessageResult.toMessageResultDto(): MessageResultDto =
    MessageResultDto(
        email = this.email,
        message = this.message
    )

fun UserCredentials.toUserCredentialsDto(): UserCredentialsDto =
    UserCredentialsDto(
        email = this.email,
        password = this.password
    )

fun ChatRoom.toChatRoomDto(): ChatRoomDto =
    ChatRoomDto(
        name = this.name
    )

fun OpenAIRequest.toOpenAIRequestDto(): OpenAIRequestDto =
    OpenAIRequestDto(
        messages = this.messages.map { it.toChatGptMessageDto() }
    )

fun ChatGptMessage.toChatGptMessageDto(): ChatGptMessageDto =
    ChatGptMessageDto(
        content = this.content,
        role = this.role
    )