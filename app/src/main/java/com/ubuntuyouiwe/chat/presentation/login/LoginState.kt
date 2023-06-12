package com.ubuntuyouiwe.chat.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val error: String = "",
    val successMessage: String = ""

)
