package com.ubuntuyouiwe.chat.data.source.remote.chatgpt

import com.ubuntuyouiwe.chat.data.dto.chatgpt.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.OpenAIResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIService {
    @POST("v1/chat/completions")
    suspend fun completeChat(@Body request: OpenAIRequestDto): OpenAIResponseDto
}