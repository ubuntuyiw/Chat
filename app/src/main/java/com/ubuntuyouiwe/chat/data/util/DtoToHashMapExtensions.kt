package com.ubuntuyouiwe.chat.data.util

import com.google.firebase.Timestamp
import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.dto.messages.MessageResultDto

fun MessageResultDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        DatabaseFieldNames.MESSAGE.fieldNames to message,
        DatabaseFieldNames.EMAIL.fieldNames to email,
        DatabaseFieldNames.DATE.fieldNames to Timestamp.now()
    )

fun UserDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        DatabaseFieldNames.UID.fieldNames to uid,
        DatabaseFieldNames.UID.fieldNames to email
    )

