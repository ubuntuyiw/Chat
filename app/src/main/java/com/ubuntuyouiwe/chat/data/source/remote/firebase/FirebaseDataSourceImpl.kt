package com.ubuntuyouiwe.chat.data.source.remote.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.OrderBy
import com.ubuntuyouiwe.chat.data.util.toUserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging
) : FirebaseDataSource {

    override fun get(
        collection: FirebaseCollection,
        orderBy: OrderBy,
    ): Flow<Result<QuerySnapshot>> = callbackFlow {

        val registration = fireStore.collection(collection.name)
            .orderBy(orderBy.field, orderBy.direction)
            .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
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
        awaitClose {
            registration.remove()
        }
    }

    override suspend fun insert(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection
    ) {

        val result =
            fireStore.collection(collection.name).add(data).await()

        data["id"] = result.id
        result.update(data)

    }

    override suspend fun findDocumentId(
        field: String,
        value: Any?,
        collection: FirebaseCollection
    ): QuerySnapshot =
        fireStore.collection(collection.name).whereEqualTo(field, value).get().await()

    override suspend fun update(
        data: HashMap<String, Any?>,
        collection: FirebaseCollection,
        documentId: String

    ) {
        fireStore.collection(collection.name).document(documentId).update(data).await()
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun loginIn(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun authState(): Flow<FirebaseAuth> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun firebaseMessaging(): FirebaseMessaging = messaging


}