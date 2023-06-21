package com.ubuntuyouiwe.chat.presentation.chat

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ubuntuyouiwe.chat.presentation.components.Message
import com.ubuntuyouiwe.chat.presentation.components.MessageInputBox
import com.ubuntuyouiwe.chat.presentation.components.SpecialSnackBar
import com.ubuntuyouiwe.chat.presentation.components.SpecialTopBar

@Composable
fun Chat() {

    val viewModel: ChatViewModel = hiltViewModel()

    var value by remember {
        mutableStateOf("")
    }
    val getState by remember {
        viewModel.stateGet
    }

    val insertState by remember {
        viewModel.stateInsert
    }

    val chatGptState = viewModel.chatGptState
    val snackbarHostState = remember { SnackbarHostState() }

    val messages = getState.listMessageResult?.messageResult
    val isFromCache = getState.listMessageResult?.isFromCache
    val isLoading = getState.isLoading
    val error = getState.errorMessage




    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SpecialTopBar(title = "Chat", viewModel.chatGptState.value.isLoading, isFromCache = isFromCache) {
                viewModel.onEvent(ChatEvent.LogOut())

            }
        },
        bottomBar = {

            MessageInputBox(value = value, onValueChange = { value = it }) {
                viewModel.onEvent(ChatEvent.SendMessage(value,true))
                value = ""
            }
        },
        snackbarHost = {
            if (viewModel.chatGptState.value.error.isNotBlank()){
                SpecialSnackBar(hostState = snackbarHostState, isLoading = false, errorMessage = viewModel.chatGptState.value.error, success = "")
            }
            if (insertState.error.isNotBlank()) {
                SpecialSnackBar(hostState = snackbarHostState, isLoading = false, errorMessage = insertState.error, success = "")
            }
        }
    ) { paddingValues ->

        if (messages.isNullOrEmpty() && !isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = "Not Data")
            }

        } else if (!isLoading) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                reverseLayout = true
            ) {
                getState.listMessageResult?.messageResult.let {
                    it?.let { messageResults ->
                        itemsIndexed(messageResults) { _, item ->

                            Message(
                                messageOwner = Firebase.auth.currentUser?.email == item.email,
                                email = item.email.toString(),
                                content = item.message.toString(),
                                hasPendingWrites = item.hasPendingWrites
                            )
                        }
                    }

                }
            }

        }
        if (error.isNotBlank() && !isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = getState.errorMessage)
            }
        }

        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }


    }


}