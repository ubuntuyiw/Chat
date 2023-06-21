package com.ubuntuyouiwe.chat.data.source.remote.chatgpt

import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.response.OpenAIResponseDto

interface OpenAIDatasource {

    suspend fun completeChat(request: OpenAIRequestDto): OpenAIResponseDto
}