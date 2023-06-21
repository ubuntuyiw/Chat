package com.ubuntuyouiwe.chat.domain.use_case.firestore

import com.ubuntuyouiwe.chat.domain.model.ChatRoom
import com.ubuntuyouiwe.chat.domain.repository.ChatRoomRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) {

    operator fun invoke(name: ChatRoom): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        try {
            chatRoomRepository.createRoom(name = name)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}