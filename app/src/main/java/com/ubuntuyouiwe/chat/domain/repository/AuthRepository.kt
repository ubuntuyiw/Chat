package com.ubuntuyouiwe.chat.domain.repository

import com.google.firebase.auth.AuthResult
import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(userCredentials: UserCredentials)

    suspend fun loginIn(userCredentials: UserCredentials)

    fun authStateListener(): Flow<User?>
}