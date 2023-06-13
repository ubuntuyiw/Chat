package com.ubuntuyouiwe.chat.presentation.chat

data class InsertState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false
)
