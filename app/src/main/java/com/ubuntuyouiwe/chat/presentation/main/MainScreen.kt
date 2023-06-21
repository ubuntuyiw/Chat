package com.ubuntuyouiwe.chat.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ubuntuyouiwe.chat.presentation.main.components.CreateRoomDialog
import com.ubuntuyouiwe.chat.presentation.components.SpecialTopBar
import com.ubuntuyouiwe.chat.presentation.main.components.ChatRoomItem
import com.ubuntuyouiwe.chat.presentation.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {

    val viewModel: MainScreenViewModel = hiltViewModel()

    var checkAlertDialog by remember {
        mutableStateOf(false)
    }
    var chatRoomName by remember {
        mutableStateOf("")
    }
    var checkBoxAI by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            SpecialTopBar(title = "Chat Rooms", false) {

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { checkAlertDialog = true }) {

            }
        },

    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 32.dp)
                .fillMaxSize()
        ) {

            ChatRoomItem() {
                navController.navigate(Screen.CHAT.name)
            }
            ChatRoomItem() {

            }




        }

        if (checkAlertDialog) {
            CreateRoomDialog(
                chatRoomName = chatRoomName,
                chatRoomNameChange = { chatRoomName = it },
                checkAlertDialogChange = { checkAlertDialog = it },
                createRoomClick = {
                    viewModel.onEvent(MainEvent.CreateRoomChat(chatRoomName))
                    chatRoomName = ""
                    checkAlertDialog = false
                },
                checkBoxAI = checkBoxAI,
                checkBoxAIdChange = {
                    checkBoxAI = it
                }

            )
        }
    }
}