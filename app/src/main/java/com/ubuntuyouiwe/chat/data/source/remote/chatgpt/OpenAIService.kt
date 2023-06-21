package com.ubuntuyouiwe.chat.data.source.remote.chatgpt

import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.response.OpenAIResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIService {
    @POST("v1/chat/completions")
    suspend fun completeChat(@Body request: OpenAIRequestDto): OpenAIResponseDto
}