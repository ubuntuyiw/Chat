package com.ubuntuyouiwe.chat.data.repository

import com.ubuntuyouiwe.chat.data.dto.UserDto
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.toUser
import com.ubuntuyouiwe.chat.data.util.toUserDto
import com.ubuntuyouiwe.chat.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStatusRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {


}