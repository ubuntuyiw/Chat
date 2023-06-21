package com.ubuntuyouiwe.chat.data.source.remote.chatgpt

import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.response.OpenAIResponseDto
import javax.inject.Inject

class OpenAIDatasourceImpl @Inject constructor(
    private val openAIService: OpenAIService
): OpenAIDatasource {
    override suspend fun completeChat(request: OpenAIRequestDto): OpenAIResponseDto {
        return openAIService.completeChat(request)
    }

}