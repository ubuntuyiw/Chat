package com.ubuntuyouiwe.chat.domain.use_case.firestore

import com.google.firebase.firestore.FirebaseFirestoreException
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUseCase @Inject constructor(
    private val messagingRepository: MessagingRepository
) {

    operator fun invoke(messageResult: MessageResult): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        try {
            messagingRepository.insertMessage(messageResult)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}