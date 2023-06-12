package com.ubuntuyouiwe.chat.domain.use_case.auth

import com.google.firebase.auth.FirebaseAuthException
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(param: UserCredentials): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        try {
            authRepository.signUp(param)
            emit(Resource.Success())
        } catch (e: FirebaseAuthException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}