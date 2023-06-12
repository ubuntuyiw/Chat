package com.ubuntuyouiwe.chat.data.util

import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.chat.data.dto.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.MessagesDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.model.User

fun FirebaseUser.toUserDto(): UserDto =
    UserDto(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl,
        isEmailVerified = this.isEmailVerified,
        phoneNumber = this.phoneNumber
    )

fun UserDto.toUser(): User =
    User(
        email = this.email
    )

fun MessageResultDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "message" to message,
        "email" to email,
        "date" to date
    )

fun MessageResultDto.toMessageResult(): MessageResult =
    MessageResult(
        message = this.message,
        email = this.email,
        date = this.date,
        hasPendingWrites = this.hasPendingWrites
    )

fun MessagesDto.toMessage(): Messages =
    Messages(
        messageResult = this.messageResultDto.map { it.toMessageResult() },
        isFromCache = this.isFromCache
    )