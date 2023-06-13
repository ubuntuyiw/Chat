package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore.FireStoreDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.domain.util.toMessageResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessagingRepositoryImpl @Inject constructor(
    private val fireStore: FireStoreDataSource
) : MessagingRepository {

    override suspend fun insertMessage(messageResult: MessageResult) {
        fireStore.insert(
            messageResult.toMessageResultDto(),
            collection = FirebaseCollection.Message2
        )
    }


    @Throws(FirebaseFirestoreException::class)
    override fun getMessage(): Flow<Messages?> {
        return fireStore.get(
            collection = FirebaseCollection.Message2,
            orderBy = OrderBy("date", Query.Direction.DESCENDING),
        ).map {
            if (it.isSuccess) {
                val asd = Messages(
                    it.getOrNull()?.documents?.mapNotNull { documentSnapshot ->
                        var messageResult = documentSnapshot.toObject(MessageResult::class.java)
                        messageResult = messageResult?.copy(
                            hasPendingWrites = documentSnapshot.metadata.hasPendingWrites(),
                        )
                        messageResult
                    },
                    isFromCache = it.getOrNull()?.metadata?.isFromCache

                )
                asd
            } else {
                throw it.exceptionOrNull()!!
            }
        }
    }
}