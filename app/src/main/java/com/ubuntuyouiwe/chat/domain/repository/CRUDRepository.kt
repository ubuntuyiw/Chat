package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.data.util.FirebaseCollection

interface CRUDRepository {

    suspend fun create(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection
    )

    suspend fun update(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection,
        documentId: String
    )

    fun read()

    fun delete()
}