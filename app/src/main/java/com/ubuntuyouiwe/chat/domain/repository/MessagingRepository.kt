package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {

    suspend fun insertMessage(messageResult: MessageResult)

    fun getMessage():  Flow<Messages?>
}