package com.ubuntuyouiwe.chat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.chat.ChatApp
import com.ubuntuyouiwe.chat.data.source.local.DataStoreDataSource
import com.ubuntuyouiwe.chat.data.source.local.DataStoreDataSourceImpl
import com.ubuntuyouiwe.chat.data.source.local.util.dataStore
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFireStoreDataSource(
        fireStore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseMessaging: FirebaseMessaging
    ): FirebaseDataSource {

        fireStore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
        return FirebaseDataSourceImpl(fireStore, firebaseAuth, firebaseMessaging)

    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
       return context.dataStore
    }

    @Singleton
    @Provides
    fun provideDataStoreDataSource(dataStore: DataStore<Preferences>): DataStoreDataSource =
        DataStoreDataSourceImpl(dataStore)

}