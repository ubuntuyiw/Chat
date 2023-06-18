package com.ubuntuyouiwe.chat.data.source.remote.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    fun get(
        collection: FirebaseCollection,
        orderBy: OrderBy,
    ): Flow<Result<QuerySnapshot>>


    suspend fun insert(data: HashMap<String, Any?>, collection: FirebaseCollection, )


    suspend fun signUp(email: String, password: String): AuthResult

    suspend fun loginIn(email: String, password: String): AuthResult

    fun authState(): Flow<FirebaseAuth>

    suspend fun firebaseMessaging(): FirebaseMessaging

    suspend fun update(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection,
        documentId: String

    )
    fun signOut()

    suspend fun findDocumentId(field: String, value: Any?, collection: FirebaseCollection): QuerySnapshot

}