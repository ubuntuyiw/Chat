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

        val documentId = firebaseDatasource.whereEqualToDocument(
            WhereEqualTo(DatabaseFieldNames.EMAIL, email),
            FirebaseCollection.Users
        ).documents.firstOrNull()?.id

        documentId?.let {
            firebaseDatasource.update(
                FirebaseCollection.Users,
                documentId = it,
                data = hashMapOf(DatabaseFieldNames.DEVICE_TOKEN.fieldNames to FieldValue.delete())
            )
        }

        try {
            firebaseDatasource.signOut()
        } catch (e: Exception) {
            documentId?.let {

                firebaseDatasource.update(
                    FirebaseCollection.Users,
                    documentId = it,
                    data = hashMapOf(DatabaseFieldNames.DEVICE_TOKEN.fieldNames to firebaseDatasource.getDeviceToken())
                )

            }
            throw Exception(e)
        }
    }

    override fun listenUserOnlineStatus(): Flow<User?> {

        return firebaseDatasource.userStateListener().map { userDto ->

            userDto?.also { user ->
                val documentId = firebaseDatasource.whereEqualToDocument(
                    whereEqualTo = WhereEqualTo(DatabaseFieldNames.EMAIL, user.email),
                    collection = FirebaseCollection.Users
                ).documents.firstOrNull()?.id

                documentId?.let { id ->
                    firebaseDatasource.update(
                        data = hashMapOf(
                            DatabaseFieldNames.LAST_ENTRY_DATE.fieldNames to Timestamp.now(),
                            DatabaseFieldNames.DEVICE_TOKEN.fieldNames to firebaseDatasource.getDeviceToken()
                        ),
                        documentId = id,
                        collection = FirebaseCollection.Users
                    )
                } ?: run {
                    firebaseDatasource.add(
                        data = hashMapOf(
                            DatabaseFieldNames.EMAIL.fieldNames to user.email,
                            DatabaseFieldNames.DEVICE_TOKEN.fieldNames to firebaseDatasource.getDeviceToken(),
                            DatabaseFieldNames.LAST_ENTRY_DATE.fieldNames to Timestamp.now(),
                        ),
                        collection = FirebaseCollection.Users
                    )
                }
            }?.toUser()

        }
    }


}