package com.ubuntuyouiwe.chat.data.source.remote.firebase.auth

import com.google.firebase.auth.AuthResult
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    suspend fun signUp(userCredentialsDto: UserCredentialsDto): AuthResult

    suspend fun loginIn(userCredentialsDto: UserCredentialsDto): AuthResult

    fun authState(): Flow<UserDto?>


}