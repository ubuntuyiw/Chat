package com.ubuntuyouiwe.chat.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.chat.domain.model.ChatRoom
import com.ubuntuyouiwe.chat.domain.use_case.firestore.CreateChatRoomUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val createChatRoomUseCase: CreateChatRoomUseCase
): ViewModel() {
    private val _createChatRoomState: MutableState<ChatRoomState> = mutableStateOf(ChatRoomState())
    private val createChatRoomState: State<ChatRoomState> = _createChatRoomState


    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.CreateRoomChat -> {
                val data = ChatRoom(
                    name = event.roomName
                )
                createChatRoom(data)
            }
        }

    }

    private fun createChatRoom(name: ChatRoom) {
        createChatRoomUseCase(name).onEach {
            when (it) {
                is Resource.Success -> {
                    _createChatRoomState.value = createChatRoomState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        error = ""
                    )
                }
                is Resource.Loading -> {
                    _createChatRoomState.value = createChatRoomState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _createChatRoomState.value = createChatRoomState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = it.message.toString()
                    )
                }
            }

        }.launchIn(viewModelScope)
    }



}