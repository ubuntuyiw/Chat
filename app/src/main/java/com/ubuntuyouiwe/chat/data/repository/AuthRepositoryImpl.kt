package com.ubuntuyouiwe.chat.data.repository

import com.ubuntuyouiwe.chat.data.source.remote.firebase.auth.AuthDataSource
import com.ubuntuyouiwe.chat.data.util.toUser
import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.util.toUserCredentialsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authentication: AuthDataSource,
) : AuthRepository {

    override suspend fun signUp(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        authentication.signUp(userCredentialsDto)
    }

    override suspend fun loginIn(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        authentication.loginIn(userCredentialsDto)
    }

    override fun authStateListener(): Flow<User?> {
        return authentication.authState().map {
            it?.toUser()
        }
    }
}