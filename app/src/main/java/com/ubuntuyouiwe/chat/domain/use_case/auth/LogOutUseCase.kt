package com.ubuntuyouiwe.chat.domain.use_case.auth

import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<Any>> = flow{
        emit(Resource.Loading())
        try {
            emit(Resource.Success(authRepository.logOut()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }


    }

}