package com.ubuntuyouiwe.chat.data.source.remote.firebase.auth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.util.toUserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthDataSource {


    @Throws(FirebaseException::class)
    override suspend fun signUp(userCredentialsDto: UserCredentialsDto): AuthResult {
        val email = userCredentialsDto.email
        val password = userCredentialsDto.password
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    @Throws(FirebaseException::class)
    override suspend fun loginIn(userCredentialsDto: UserCredentialsDto): AuthResult {
        val email = userCredentialsDto.email
        val password = userCredentialsDto.password
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override fun authState(): Flow<UserDto?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            firebaseAuth.currentUser?.let { firebaseUser ->
                trySend(firebaseUser.toUserDto())
            } ?: trySend(null)

        }
        auth.addAuthStateListener(listener)

        awaitClose { auth.removeAuthStateListener(listener) }

    }


}
