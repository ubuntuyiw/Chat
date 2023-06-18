package com.ubuntuyouiwe.chat.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSourceImpl @Inject constructor(
    private val datastore: DataStore<Preferences>,
) : DataStoreDataSource {

    override suspend fun<T> insertLocalStore(key: Preferences.Key<T>, value: T) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun<T> getLocalStore(key: Preferences.Key<T>): Flow<T?> {
        return datastore.data.map {
            it[key]
        }
    }

    override suspend fun<T> deleteLocalStore(key: Preferences.Key<T>) {
        datastore.edit { preferences ->
            preferences.remove(key)
        }
    }

}