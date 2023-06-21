package com.ubuntuyouiwe.chat.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.ChatGptMessageDto
import com.ubuntuyouiwe.chat.data.dto.chatgpt.request.OpenAIRequestDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.source.remote.chatgpt.OpenAIDatasource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.toHashMap
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.model.chatgpt.request.OpenAIRequest
import com.ubuntuyouiwe.chat.domain.model.chatgpt.response.OpenAIResponse
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.domain.util.toMessageResultDto
import com.ubuntuyouiwe.chat.domain.util.toOpenAIRequestDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MessagingRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseDataSource,
    private val chatGpt: OpenAIDatasource
) : MessagingRepository {

    override suspend fun insertMessage(messageResult: MessageResult) {
        val messageDtoToHashMap = messageResult.toMessageResultDto().toHashMap()

        fireStore.add(
            data = messageDtoToHashMap,
            collection = FirebaseCollection.Message
        )

    }

    override suspend fun chatGpt(message: OpenAIRequest) {
        val data = message.toOpenAIRequestDto()
        coroutineScope {
            val result = async {
                chatGpt.completeChat(data)
            }
            val fff = result.await().choices.map { it.message }.firstOrNull()
            fireStore.add(
                MessageResultDto(
                    email = "AI",
                    message = fff?.content
                ).toHashMap(),
                FirebaseCollection.Message
            )
        }


    }


    @Throws(FirebaseFirestoreException::class)
    override fun getMessage(): Flow<Messages?> {
        return fireStore.addSnapshotListener(
            collection = FirebaseCollection.Message,
            orderBy = OrderBy("date", Query.Direction.DESCENDING),
        ).map {
            if (it.isSuccess) {
                Messages(
                    it.getOrNull()?.documents?.mapNotNull { documentSnapshot ->
                        var messageResult = documentSnapshot.toObject(MessageResult::class.java)
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