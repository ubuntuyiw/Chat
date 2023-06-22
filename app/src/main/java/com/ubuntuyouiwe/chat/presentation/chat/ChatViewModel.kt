package com.ubuntuyouiwe.chat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.use_case.auth.LogOutUseCase
import com.ubuntuyouiwe.chat.domain.use_case.firestore.GetMessageUseCase
import com.ubuntuyouiwe.chat.domain.use_case.firestore.InsertUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {

    private val _stateInsert = mutableStateOf(InsertState())
    val stateInsert: State<InsertState> = _stateInsert

    private val _stateGet = mutableStateOf(GetState())
    val stateGet: State<GetState> = _stateGet

    private val _stateLogOut = mutableStateOf(LogOutState())
    val stateLogOut: State<LogOutState> = _stateLogOut


    private var getMessageJob: Job = Job()

    init {
        getMessage()
    }

    fun onEvent(event: ChatEvent) {
        val messageResult =
            MessageResult(email = Firebase.auth.currentUser?.email, message = event.message)
        when (event) {
            is ChatEvent.SendMessage -> {
                insertMessage(messageResult)
            }

            is ChatEvent.LogOut -> {
                logOut()
            }
        }
    }

    private fun logOut() {
        logOutUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _stateLogOut.value = stateLogOut.value.copy(isLoading = true, errorMessage = "")
                }

                is Resource.Success -> {
                    _stateLogOut.value =
                        stateLogOut.value.copy(isLoading = false, errorMessage = "")
                }

                is Resource.Error -> {
                    _stateLogOut.value = stateLogOut.value.copy(
                        isLoading = false,
                        errorMessage = it.message.toString()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun insertMessage(messageResult: MessageResult) {
        insertUseCase(messageResult).onEach {
            when (it) {
                is Resource.Success -> {
                    _stateInsert.value =
                        stateInsert.value.copy(isLoading = false, error = "", isSuccess = true)
                }

                is Resource.Error -> {
                    _stateInsert.value = stateInsert.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = it.message.toString()
                    )
                }

                is Resource.Loading -> {
                    _stateInsert.value =
                        stateInsert.value.copy(isLoading = true, error = "", isSuccess = false)
                }


            }
        }.launchIn(viewModelScope)
    }

    private fun getMessage() {
        getMessageJob.cancel()
        getMessageJob = getMessageUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _stateGet.value = stateGet.value.copy(
                        isLoading = false,
                        listMessageResult = it.data,
                        errorMessage = ""
                    )
                }

                is Resource.Error -> {
                    _stateGet.value = stateGet.value.copy(
                        isLoading = false,
                        errorMessage = it.message.toString()
                    )
                }

                is Resource.Loading -> {
                    _stateGet.value =
                        stateGet.value.copy(isLoading = true, errorMessage = "")
                    delay(500) //TODO
                }
            }
        }.launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        getMessageJob.cancel()
    }


}