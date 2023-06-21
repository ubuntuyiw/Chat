package com.ubuntuyouiwe.chat.data.repository

import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.util.FirebaseCollection
import com.ubuntuyouiwe.chat.data.util.asHashMap
import com.ubuntuyouiwe.chat.domain.model.ChatRoom
import com.ubuntuyouiwe.chat.domain.repository.ChatRoomRepository
import com.ubuntuyouiwe.chat.domain.util.toChatRoomDto
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
): ChatRoomRepository {

    override suspend fun createRoom(name: ChatRoom) {
        val data = name.toChatRoomDto().copy(
            photoURL = "",
            totalMessages = 0,
            memberIds = listOf(firebaseDataSource.user()?.uid.toString())
        )

        firebaseDataSource.add(
            collection = FirebaseCollection.ChatRooms,
            data = data.asHashMap()

        )
    }
}