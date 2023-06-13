package com.ubuntuyouiwe.chat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.ubuntuyouiwe.chat.data.source.remote.firebase.auth.AuthDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.auth.AuthDataSourceImpl
import com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore.FireStoreDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore.FireStoreDataSourceImpl
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
    fun provideAuthenticationDataSource(firebaseAuth: FirebaseAuth): AuthDataSource =
        AuthDataSourceImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideFireStoreDataSource(fireStore: FirebaseFirestore): FireStoreDataSource {

        fireStore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = false
        }
        return FireStoreDataSourceImpl(fireStore)

    }
}