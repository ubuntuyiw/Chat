package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.DatabaseFieldNames
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.WhereEqualTo
import com.ubuntuyouiwe.chat.data.util.toUser
import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.util.toUserCredentialsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseDatasource: FirebaseDataSource,
) : AuthRepository {

    override suspend fun signUp(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        firebaseDatasource.signUp(userCredentialsDto.email, userCredentialsDto.password)
    }

    override suspend fun loginIn(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        val email = userCredentialsDto.email
        val password = userCredentialsDto.password
        firebaseDatasource.loginIn(email, password)
    }

    override fun user() = firebaseDatasource.user()?.toUser()

    @Throws
    override suspend fun logOut() {
        val email = firebaseDatasource.user()?.email

        val document = firebaseDatasource.whereEqualToDocument(
            WhereEqualTo(DatabaseFieldNames.EMAIL, email),
            FirebaseCollection.Users
        ).documents
        val documentId = document.firstOrNull()?.id

        val deviceTokens = document.firstOrNull()?.get(DatabaseFieldNames.DEVICE_TOKEN.fieldNames) as MutableList<*>
        deviceTokens.remove(firebaseDatasource.getDeviceToken())

        documentId?.let {
            firebaseDatasource.update(
                data = hashMapOf(
                    DatabaseFieldNames.DEVICE_TOKEN.fieldNames to deviceTokens,
                ),
                collection = FirebaseCollection.Users,
                documentId = it
            )
        }


        try {
            firebaseDatasource.signOut()
        } catch (e: Exception) {
            documentId?.let {

                firebaseDatasource.update(
                    FirebaseCollection.Users,
                    documentId = it,
                    data = hashMapOf(DatabaseFieldNames.DEVICE_TOKEN.fieldNames to  listOf(firebaseDatasource.getDeviceToken()))
                )

            }
            throw Exception(e)
        }
    }


    override fun listenUserOnlineStatus(): Flow<User?> {
        return firebaseDatasource.userStateListener().map { userDto ->
            userDto?.also { user ->
                val document = firebaseDatasource.whereEqualToDocument(
                    WhereEqualTo(DatabaseFieldNames.EMAIL, user.email),
                    FirebaseCollection.Users
                ).documents

                val documentId = document.firstOrNull()?.id

                val deviceTokensObject = document.firstOrNull()?.get(DatabaseFieldNames.DEVICE_TOKEN.fieldNames)
                val deviceTokens = deviceTokensObject?.let { it as? List<*> }?.map { it.toString() }?.toMutableList()

                val deviceTokensSet = deviceTokens?.toMutableSet()

                if (deviceTokensSet?.contains(firebaseDatasource.getDeviceToken()) == false) {
                    deviceTokensSet.add(firebaseDatasource.getDeviceToken() ?: "")
                }

                documentId?.let { id ->
                    firebaseDatasource.update(
                        data = hashMapOf(
                            DatabaseFieldNames.LAST_ENTRY_DATE.fieldNames to Timestamp.now(),
                            DatabaseFieldNames.DEVICE_TOKEN.fieldNames to deviceTokensSet?.toList()
                        ),
                        documentId = id,
                        collection = FirebaseCollection.Users
                    )
                } ?: run {
                    firebaseDatasource.add(
                        data = hashMapOf(
                            DatabaseFieldNames.EMAIL.fieldNames to user.email,
                            DatabaseFieldNames.DEVICE_TOKEN.fieldNames to deviceTokensSet?.toList(),
                            DatabaseFieldNames.LAST_ENTRY_DATE.fieldNames to Timestamp.now(),
                        ),
                        collection = FirebaseCollection.Users
                    )
                }
            }?.toUser()
        }
    }


}