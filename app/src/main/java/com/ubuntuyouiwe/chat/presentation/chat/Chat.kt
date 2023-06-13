package com.ubuntuyouiwe.chat.presentation.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ubuntuyouiwe.chat.presentation.components.Message
import com.ubuntuyouiwe.chat.presentation.components.MessageInputBox
import com.ubuntuyouiwe.chat.presentation.components.SpecialTopBar

@Composable
fun Chat() {

    val viewModel: ChatViewModel = hiltViewModel()

    var value by remember {
        mutableStateOf("")
    }
    val stateGet = viewModel.stateGet.collectAsStateWithLifecycle()


    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SpecialTopBar(title = "Chat", isFromCache = stateGet.value.listMessageResult?.isFromCache) {
                Firebase.auth.signOut()
            }
        },
        bottomBar = {

            MessageInputBox(value = value, onValueChange = { value = it }) {
                viewModel.onEvent(ChatEvent.SendMessage(value))
                value = ""
            }
        }
    ) { paddingValues ->

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            reverseLayout = true
        ) {
            stateGet.value.listMessageResult?.messageResult.let {
                it?.let {messageResults ->
                    if (messageResults.isEmpty()) {
                        item {
                            Text(text = "Not Data")
                        }
                    }
                    else {
                        itemsIndexed(messageResults) { _, b ->
                            Message(
                                Firebase.auth.currentUser?.email == b.email,
                                email = b.email.toString(),
                                content = b.message.toString(),
                                hasPendingWrites = b.hasPendingWrites
                            )
                        }
                    }
                }

            }

            if (stateGet.value.errorMessage.isNotBlank()) {
                item {
                    Text(text = stateGet.value.errorMessage)
                }
            }

        }


    }


}