package com.ubuntuyouiwe.chat.data.util

import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto

fun MessageResultDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "message" to message,
        "email" to email,
        "date" to date
    )

fun UserDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "uid" to uid,
        "email" to email
    )

