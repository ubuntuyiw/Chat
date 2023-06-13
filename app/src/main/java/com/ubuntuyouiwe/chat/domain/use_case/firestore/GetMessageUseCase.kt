package com.ubuntuyouiwe.chat.domain.use_case.firestore

import com.google.firebase.firestore.FirebaseFirestoreException
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    private val messagingRepository: MessagingRepository
) {

    operator fun invoke(): Flow<Resource<Messages>> = flow {
        emit(Resource.Loading())
        try {
            messagingRepository.getMessage().collect {
                emit(Resource.Success(it))
            }

        } catch (e: CancellationException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: FirebaseFirestoreException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }


}