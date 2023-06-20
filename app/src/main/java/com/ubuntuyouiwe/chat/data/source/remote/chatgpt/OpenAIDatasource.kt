package com.ubuntuyouiwe.chat.data.source.remote.chatgpt

import com.ubuntuyouiwe.chat.data.dto.chatgpt.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.OpenAIResponseDto

interface OpenAIDatasource {

    suspend fun completeChat(request: OpenAIRequestDto): OpenAIResponseDto
}