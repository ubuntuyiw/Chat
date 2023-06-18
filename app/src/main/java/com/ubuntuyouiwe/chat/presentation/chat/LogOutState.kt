package com.ubuntuyouiwe.chat.presentation.chat

import com.ubuntuyouiwe.chat.domain.model.Messages

data class LogOutState(
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
