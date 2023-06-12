package com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.ubuntuyouiwe.chat.data.dto.MessageResultDto
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.Pagination
import com.ubuntuyouiwe.chat.data.util.toHashMap
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : FireStoreDataSource {

    private var totalPage: Long = 0
    private var registration: ListenerRegistration? = null
    override fun get(
        collection: FirebaseCollection,
        orderBy: OrderBy,
        initialLimit: Long,
        nextPageLimit: Long,
        pagination: Pagination

    ): Flow<Result<QuerySnapshot>> = callbackFlow {
        registration?.remove()

        var query : Query? = null

        when (pagination) {
            Pagination.REFRESH -> {
                totalPage = initialLimit

                query = fireStore.collection(collection.name)
                    .orderBy(orderBy.field, orderBy.direction)
                    .limit(initialLimit)
            }

            Pagination.NEXT -> {
                totalPage += nextPageLimit
                query = fireStore.collection(collection.name)
                    .orderBy(orderBy.field, orderBy.direction)
                    .limit(totalPage)
            }
        }


        registration =  query.addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                error?.let {
                    trySendBlocking(
                        Result.failure(
                            FirebaseFirestoreException(
                                it.localizedMessage ?: "Unknown",
                                it.code
                            )
                        )
                    )

                }

                value?.let {
                    trySend(Result.success(it))
                } ?: kotlin.run {

                    trySendBlocking(
                        Result.failure(
                            FirebaseFirestoreException(
                                "No data found for the requested document",
                                FirebaseFirestoreException.Code.NOT_FOUND
                            )
                        )
                    )
                }
            }
        awaitClose { registration?.remove() }
    }

    override suspend fun insert(messageResultDto: MessageResultDto) {
        val messageDtoToHashMap = messageResultDto.toHashMap()
        val result =
            fireStore.collection(FirebaseCollection.Message.name).add(messageDtoToHashMap)
                .await()

        messageDtoToHashMap["id"] = result.id
        result.update(messageDtoToHashMap)


    }

}