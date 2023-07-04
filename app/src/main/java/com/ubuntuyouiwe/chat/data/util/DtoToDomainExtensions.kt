package com.ubuntuyouiwe.chat.data.util

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.Messages
import com.ubuntuyouiwe.chat.domain.model.User
import java.text.SimpleDateFormat
import java.util.Locale

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

private fun dateFormat(date: Timestamp?): String? {
    return date?.let {
        val milliseconds = it.seconds * 1000 + it.nanoseconds / 1000000
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.format(milliseconds)
    }


}
fun MessageResultDto.toMessageResult(): MessageResult =
    MessageResult(
        message = this.message,
        email = this.email,
        date = dateFormat(this.date),
        hasPendingWrites = this.hasPendingWrites
    )

fun MessagesDto.toMessage(): Messages =
    Messages(
        messageResult = this.messageResultDto.map { it.toMessageResult() },
        isFromCache = this.isFromCache
    )