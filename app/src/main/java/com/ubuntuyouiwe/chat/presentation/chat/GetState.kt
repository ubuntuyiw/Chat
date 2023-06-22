package com.ubuntuyouiwe.chat.presentation.chat

import com.ubuntuyouiwe.chat.domain.model.Messages

data class GetState(
    val isLoading: Boolean = false,
    val listMessageResult: Messages? = null,
    val errorMessage: String = ""

)
