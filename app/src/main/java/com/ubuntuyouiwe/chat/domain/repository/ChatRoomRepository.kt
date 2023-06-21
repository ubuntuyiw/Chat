package com.ubuntuyouiwe.chat.domain.repository

import com.ubuntuyouiwe.chat.domain.model.ChatRoom

interface ChatRoomRepository {
    suspend fun createRoom(name: ChatRoom)
}