package com.ubuntuyouiwe.chat.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ubuntuyouiwe.chat.data.util.Pagination
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.use_case.firestore.GetMessageUseCase
import com.ubuntuyouiwe.chat.domain.use_case.firestore.InsertUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase,
    private val getMessageUseCase: GetMessageUseCase,
) : ViewModel() {

    private val _stateInsert = MutableStateFlow(InsertState())
    val stateInsert: StateFlow<InsertState> = _stateInsert.asStateFlow()

    private val _stateGet = MutableStateFlow(GetState())
    val stateGet: StateFlow<GetState> = _stateGet.asStateFlow()


    init {
        getMessage(Pagination.REFRESH)
    }

    fun onEvent(event: ChatEvent) {
        val messageResult =
            MessageResult(email = Firebase.auth.currentUser?.email, message = event.message)
        when (event) {
            is ChatEvent.SendMessage -> {
                insertMessage(messageResult)
            }
        }
    }

    private fun insertMessage(messageResult: MessageResult) {
        insertUseCase(messageResult).onEach {
            when (it) {
                is Resource.Success -> {
                    _stateInsert.value = stateInsert.value.copy(isLoading = false, isSuccess = true)
                }

                is Resource.Error -> {
                    _stateInsert.value = stateInsert.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = it.message.toString()
                    )
                }

                is Resource.Loading -> {
                    _stateInsert.value = stateInsert.value.copy(isLoading = true, isSuccess = false)
                }


            }
        }.launchIn(viewModelScope)
    }

    fun getMessage(pagination: Pagination) {
        getMessageUseCase(pagination).onEach {
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
                        stateGet.value.copy(isLoading = false, errorMessage = "")
                }
            }
        }.launchIn(viewModelScope)

    }


}