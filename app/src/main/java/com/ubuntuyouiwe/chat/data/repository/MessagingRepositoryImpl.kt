package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.RemoteMessage
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.DatabaseFieldNames
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.toHashMap
import com.ubuntuyouiwe.chat.data.util.toMessageResult
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.domain.util.toMessageResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessagingRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseDataSource,
) : MessagingRepository {

    override suspend fun insertMessage(messageResult: MessageResult) {
        val messageDtoToHashMap = messageResult.toMessageResultDto().toHashMap()

        fireStore.add(
            data = messageDtoToHashMap,
            collection = FirebaseCollection.Message
        )
    }


    @Throws(FirebaseFirestoreException::class)
    override fun getMessage(): Flow<Messages?> {
        return fireStore.addSnapshotListener(
            collection = FirebaseCollection.Message,
            orderBy = OrderBy(DatabaseFieldNames.DATE, Query.Direction.DESCENDING),
        ).map {
            if (it.isSuccess) {
                Messages(
                    it.getOrNull()?.documents?.mapNotNull { documentSnapshot ->
                        var messageResult = documentSnapshot.toObject(MessageResultDto::class.java)?.toMessageResult()
                        messageResult = messageResult?.copy(
                            hasPendingWrites = documentSnapshot.metadata.hasPendingWrites(),
                        )
                        messageResult

                    },
                    isFromCache = it.getOrNull()?.metadata?.isFromCache

                )
            } else {
                throw it.exceptionOrNull()!!
            }
        }
    }
}