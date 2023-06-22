package com.ubuntuyouiwe.chat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}