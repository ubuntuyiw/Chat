package com.ubuntuyouiwe.chat.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.chat.domain.use_case.auth.AuthStateUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase
) : ViewModel() {

    private val _stateAuth = MutableStateFlow(AuthState())
    val stateAuth: StateFlow<AuthState> = _stateAuth.asStateFlow()
    private var getAuthJob: Job = Job()

    init {
        getAuthStateListener()
    }

    private fun getAuthStateListener() {
        getAuthJob.cancel()
        getAuthJob = authStateUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _stateAuth.value = stateAuth.value.copy(
                        success = it.data,
                        errorMessage = null,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _stateAuth.value = stateAuth.value.copy(
                        success = null,
                        errorMessage = it.message.toString(),
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _stateAuth.value =
                        stateAuth.value.copy(success = null, errorMessage = null, isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        getAuthJob.cancel()
    }


}