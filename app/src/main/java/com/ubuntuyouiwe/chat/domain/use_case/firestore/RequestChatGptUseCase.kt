package com.ubuntuyouiwe.chat.domain.use_case.firestore

import com.ubuntuyouiwe.chat.domain.model.chatgpt.request.OpenAIRequest
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestChatGptUseCase @Inject constructor(
    private val messagingRepository: MessagingRepository
) {
    operator fun invoke(mesagge: OpenAIRequest): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            messagingRepository.chatGpt(mesagge)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}