package com.ubuntuyouiwe.chat.data.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.source.local.DataStoreDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.toHashMap
import com.ubuntuyouiwe.chat.data.util.toUser
import com.ubuntuyouiwe.chat.data.util.toUserDto
import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.util.toUserCredentialsDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.Throws

class AuthRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseDataSource,
) : AuthRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var userDto: UserDto? = UserDto()
    override suspend fun signUp(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        val email = userCredentialsDto.email
        val password = userCredentialsDto.password
        val result = fireStore.signUp(email, password)
        val data = result.user?.toUserDto()?.toHashMap()

        createDeviceToken()?.let {
            data?.set("deviceToken", it)
        }
        addUserToDatabase(data)


    }

    override suspend fun loginIn(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        val email = userCredentialsDto.email
        val password = userCredentialsDto.password
        fireStore.loginIn(email, password)
    }

    @Throws
    override suspend fun logOut() {
        val deviceToken = hashMapOf<String, Any?>(
            "deviceToken" to createDeviceToken()
        )
        val documentId = getDocumentIdByEmail(userDto?.email, FirebaseCollection.Users)
        val deviceTokenDelete = hashMapOf<String, Any?>(
            "deviceToken" to FieldValue.delete()
        )
        documentId?.let {
            update(documentId = it, data = deviceTokenDelete, FirebaseCollection.Users)
        }

        try {
            fireStore.signOut()
        } catch (e: Exception) {
            documentId?.let {
                update(documentId = it, data = deviceToken, FirebaseCollection.Users)
            }
            throw Exception(e)
        }
    }

    override fun listenUserOnlineStatus(): Flow<User?> {

        return fireStore.authState().map { authState ->
            val currentUser = authState.currentUser

            userDto = currentUser?.also { user ->
                scope.launch {
                    val deviceToken = createDeviceToken()

                    val lastEntryDate = hashMapOf<String, Any?>(
                        "lastEntryDate" to Timestamp.now(),
                        "deviceToken" to deviceToken
                    )
                    val documentId = getDocumentIdByEmail(user.email, FirebaseCollection.Users)

                    documentId?.let {
                        update(it, lastEntryDate, FirebaseCollection.Users)
                    }
                }
            }?.toUserDto()

            userDto?.toUser()
        }
    }


    private suspend fun addUserToDatabase(data: HashMap<String, Any?>?) {
        data?.let {
            fireStore.insert(data = it, collection = FirebaseCollection.Users)
        }
    }


    override suspend fun getDocumentIdByEmail(
        email: String?,
        collection: FirebaseCollection
    ): String? {
        return fireStore.findDocumentId(
            field = "email",
            value = email,
            collection = collection
        ).documents.firstOrNull()?.id
    }

    override suspend fun update(
        documentId: String,
        data: HashMap<String, Any?>,
        collection: FirebaseCollection
    ) {

        fireStore.update(
            data = data,
            documentId = documentId,
            collection = collection
        )
    }

    private suspend fun createDeviceToken(): String? {
        return fireStore.firebaseMessaging().token.await()
    }

    fun clear() {
        scope.cancel()
    }

}