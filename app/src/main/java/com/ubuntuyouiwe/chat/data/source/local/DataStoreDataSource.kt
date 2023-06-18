package com.ubuntuyouiwe.chat.data.source.local

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {

    suspend fun<T> insertLocalStore(key: Preferences.Key<T>, value: T)

    fun<T> getLocalStore(key: Preferences.Key<T>): Flow<T?>

    suspend fun<T> deleteLocalStore(key: Preferences.Key<T>)
}