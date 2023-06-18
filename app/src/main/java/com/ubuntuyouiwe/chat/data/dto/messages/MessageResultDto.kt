package com.ubuntuyouiwe.chat.data.dto.messages

import com.google.firebase.Timestamp


data class MessageResultDto(
    val message: String? = null,
    val email: String? = null,
    val date: Timestamp = Timestamp.now(),
    val hasPendingWrites: Boolean = false,
)
