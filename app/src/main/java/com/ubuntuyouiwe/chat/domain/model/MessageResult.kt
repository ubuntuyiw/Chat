package com.ubuntuyouiwe.chat.domain.model

import com.google.firebase.Timestamp

data class MessageResult(
    val message: String? = null,
    val email: String? = null,
    val date: Timestamp = Timestamp.now(),
    val hasPendingWrites: Boolean = false,
)
