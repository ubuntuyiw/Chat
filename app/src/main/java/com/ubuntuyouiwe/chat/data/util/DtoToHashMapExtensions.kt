package com.ubuntuyouiwe.chat.data.util

import com.ubuntuyouiwe.chat.data.dto.ChatRoomDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.domain.model.ChatRoom

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

fun ChatRoomDto.asHashMap(): HashMap<String,Any?> =
    hashMapOf(
        "name" to this.name,
        "memberIds" to this.memberIds,
        "photoURL" to this.photoURL,
        "totalMessages" to this.totalMessages,
    )