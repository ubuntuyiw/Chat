package com.ubuntuyouiwe.chat.presentation.activity

import com.ubuntuyouiwe.chat.domain.model.User

data class AuthState(
    val success: User? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
