package com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore

import com.google.firebase.firestore.QuerySnapshot
import com.ubuntuyouiwe.chat.data.dto.MessageResultDto
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.Pagination
import kotlinx.coroutines.flow.Flow

interface FireStoreDataSource {

    fun get(
        collection: FirebaseCollection,
        orderBy: OrderBy,
        initialLimit: Long,
        nextPageLimit: Long,
        pagination: Pagination
    ): Flow<Result<QuerySnapshot>>


    suspend fun insert(messageResultDto: MessageResultDto)
}