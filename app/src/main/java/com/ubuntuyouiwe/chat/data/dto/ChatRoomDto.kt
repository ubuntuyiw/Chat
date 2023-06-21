package com.ubuntuyouiwe.chat.data.dto

data class ChatRoomDto(
    val id: String? = null,
    val lastMessage: String? = null,
    val memberIds: List<String>? = null,
    val name: String? = null,
    val numberOfActiveMembers: Int? = null,
    val numberOfMembers: Int? = null,
    val photoURL: String? = null,
    val totalMessages: Int? = null
)