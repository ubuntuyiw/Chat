package com.ubuntuyouiwe.chat.data.util

import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.UserDto

fun MessageResultDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "chatGptMessageDto" to message,
        "email" to email,
        "date" to date
    )

fun UserDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "uid" to uid,
        "email" to email
    )