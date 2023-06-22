package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(userCredentials: UserCredentials)

    suspend fun loginIn(userCredentials: UserCredentials)

    fun listenUserOnlineStatus(): Flow<User?>


    suspend fun logOut()

    fun user(): User?
}