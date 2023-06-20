package com.ubuntuyouiwe.chat.di

import com.ubuntuyouiwe.chat.data.repository.AuthRepositoryImpl
import com.ubuntuyouiwe.chat.data.repository.MessagingRepositoryImpl
import com.ubuntuyouiwe.chat.data.repository.NotificationRepositoryImpl
import com.ubuntuyouiwe.chat.data.source.local.DataStoreDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseDataSource: FirebaseDataSource,
    ): AuthRepository =
        AuthRepositoryImpl(firebaseDataSource)

    @Provides
    @Singleton
    fun provideMessagingRepository(
        firebaseDataSource: FirebaseDataSource,
    ): MessagingRepository =
        MessagingRepositoryImpl(firebaseDataSource)

    @Provides
    @Singleton
    fun provideNotificationService(
        firebaseDataSource: FirebaseDataSource
    ): NotificationRepository = NotificationRepositoryImpl(firebaseDataSource)


}