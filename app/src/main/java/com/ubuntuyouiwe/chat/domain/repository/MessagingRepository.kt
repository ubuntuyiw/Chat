package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.model.chatgpt.request.OpenAIRequest
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {

    suspend fun insertMessage(messageResult: MessageResult)

    fun getMessage():  Flow<Messages?>

    suspend fun chatGpt(message: OpenAIRequest)
}