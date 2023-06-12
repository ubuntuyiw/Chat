package com.ubuntuyouiwe.chat.domain.util

import com.ubuntuyouiwe.chat.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.chat.domain.model.UserCredentials


fun UserCredentials.toUserCredentialsDto(): UserCredentialsDto =
    UserCredentialsDto(
        email = this.email,
        password = this.password
    )