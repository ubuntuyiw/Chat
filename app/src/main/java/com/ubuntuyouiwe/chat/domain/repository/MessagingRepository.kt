package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.data.dto.MessagesDto
import com.ubuntuyouiwe.chat.data.util.Pagination
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {

    suspend fun insertMessage(messageResult: MessageResult)

    fun getMessage(pagination: Pagination):  Flow<Messages?>
}