package com.ubuntuyouiwe.chat.domain.util

import com.ubuntuyouiwe.chat.data.dto.MessageResultDto
import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.domain.model.MessageResult
import com.ubuntuyouiwe.chat.domain.model.UserCredentials

fun MessageResult.toMessageResultDto(): MessageResultDto =
    MessageResultDto(
        email = this.email,
        message = this.message
    )

fun UserCredentials.toUserCredentialsDto(): UserCredentialsDto =
    UserCredentialsDto(
        email = this.email,
        password = this.password
    )