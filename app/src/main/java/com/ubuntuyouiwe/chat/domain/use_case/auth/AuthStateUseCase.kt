package com.ubuntuyouiwe.chat.domain.use_case.auth

import com.ubuntuyouiwe.chat.domain.model.User
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<Resource<User?>> = flow {
        emit(Resource.Loading())
        authRepository.authStateListener().catch {
            emit(Resource.Error(it.localizedMessage))
        }.collect {
            emit(Resource.Success(it))
        }
    }

}