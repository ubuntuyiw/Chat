package com.ubuntuyouiwe.chat.data.repository

import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.domain.repository.CRUDRepository
import javax.inject.Inject

class CRUDRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : CRUDRepository {
    override suspend fun create(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection
    ) {
        firebaseDataSource.insert(data, collection)
    }

    override suspend fun update(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection,
        documentId: String
    ) {
        firebaseDataSource.update(data = data, collection = collection, documentId = documentId)
    }

    override fun read() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }


}