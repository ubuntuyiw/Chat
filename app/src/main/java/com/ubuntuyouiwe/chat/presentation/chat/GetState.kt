package com.ubuntuyouiwe.chat.presentation.chat

import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages

data class GetState(
    val isLoading: Boolean? = true,
    val listMessageResult: Messages? = null,
    val errorMessage: String = ""

)
