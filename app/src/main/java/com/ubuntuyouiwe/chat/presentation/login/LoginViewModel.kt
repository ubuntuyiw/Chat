package com.ubuntuyouiwe.chat.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.chat.domain.model.UserCredentials
import com.ubuntuyouiwe.chat.domain.use_case.auth.LoginInUseCase
import com.ubuntuyouiwe.chat.domain.use_case.auth.SignUpUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singUpUseCase: SignUpUseCase,
    private val loginInUseCase: LoginInUseCase,

    ) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()



    fun onEvent(event: LoginEvent) {
        val user = UserCredentials(event.email, event.password)
        when (event) {
            is LoginEvent.SignUp -> {
                signUp(user)
            }

            is LoginEvent.LogIn -> {
                logIn(user)

            }
        }

    }

    private fun signUp(userCredentials: UserCredentials) {
        singUpUseCase(userCredentials).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value =
                        state.value.copy(
                            isLoading = false,
                            successMessage = "Creation was successful.",
                            error = ""
                        )
                }

                is Resource.Loading -> {
                    _state.value =
                        state.value.copy(isLoading = true, successMessage = "", error = "")

                }

                is Resource.Error -> {
                    _state.value =
                        state.value.copy(
                            isLoading = false,
                            error = it.message.toString(),
                            successMessage = ""
                        )
                }

            }
        }.launchIn(viewModelScope)

    }


    private fun logIn(userCredentials: UserCredentials) {
        loginInUseCase(userCredentials).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value =
                        state.value.copy(
                            isLoading = false,
                            successMessage = "Login successful.",
                            error = ""
                        )
                }

                is Resource.Loading -> {
                    _state.value =
                        state.value.copy(isLoading = true, successMessage = "", error = "")

                }

                is Resource.Error -> {
                    _state.value =
                        state.value.copy(
                            isLoading = false,
                            error = it.message.toString(),
                            successMessage = ""
                        )
                }

            }
        }.launchIn(viewModelScope)

    }


}