package com.ubuntuyouiwe.chat.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
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

    @Throws
    override suspend fun logOut() {
        val email = firebaseDatasource.user()?.email

        val documentId = firebaseDatasource.whereEqualToDocument(
            WhereEqualTo("email", email),
            FirebaseCollection.Users
        ).documents.firstOrNull()?.id

        documentId?.let {
            firebaseDatasource.update(
                FirebaseCollection.Users,
                documentId = it,
                data = hashMapOf("deviceToken" to FieldValue.delete())
            )
        }

        try {
            firebaseDatasource.signOut()
        } catch (e: Exception) {
            documentId?.let {

                firebaseDatasource.update(
                    FirebaseCollection.Users,
                    documentId = it,
                    data = hashMapOf("deviceToken" to firebaseDatasource.getDeviceToken())
                )

            }
            throw Exception(e)
        }
    }

    override fun listenUserOnlineStatus(): Flow<User?> {

        return firebaseDatasource.userStateListener().map { userDto ->

            userDto?.also { user ->
                val documentId = firebaseDatasource.whereEqualToDocument(
                    whereEqualTo = WhereEqualTo("email", user.email),
                    collection = FirebaseCollection.Users
                ).documents.firstOrNull()?.id

                documentId?.let { id ->
                    firebaseDatasource.update(
                        data = hashMapOf(
                            "lastEntryDate" to Timestamp.now(),
                            "deviceToken" to firebaseDatasource.getDeviceToken()
                        ),
                        documentId = id,
                        collection = FirebaseCollection.Users
                    )
                }?: run {
                    firebaseDatasource.add(
                        data = hashMapOf(
                            "email" to user.email,
                            "deviceToken" to firebaseDatasource.getDeviceToken(),
                            "lastEntryDate" to Timestamp.now(),
                        ),
                        collection = FirebaseCollection.Users
                    )
                }
            }?.toUser()

        }
    }


}