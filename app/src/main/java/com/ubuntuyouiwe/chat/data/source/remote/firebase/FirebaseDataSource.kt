package com.ubuntuyouiwe.chat.data.source.remote.firebase

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.WhereEqualTo
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    fun addSnapshotListener(
        collection: FirebaseCollection,
        orderBy: OrderBy,
    ): Flow<Result<QuerySnapshot>>


    suspend fun add(data: HashMap<String, Any?>, collection: FirebaseCollection)


    suspend fun signUp(email: String, password: String)

    suspend fun loginIn(email: String, password: String)

    fun userStateListener(): Flow<UserDto?>

    fun signOut()
    fun firebaseMessaging(): FirebaseMessaging

    suspend fun update(
        collection: FirebaseCollection,
        data: HashMap<String, Any?>,
        documentId: String
    )

    fun user(): UserDto?
    suspend fun whereEqualToDocument(
        whereEqualTo: WhereEqualTo,
        collection: FirebaseCollection
    ): QuerySnapshot

    suspend fun getDeviceToken(): String?

}