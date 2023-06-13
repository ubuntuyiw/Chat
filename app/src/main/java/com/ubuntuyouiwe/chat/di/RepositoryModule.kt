package com.ubuntuyouiwe.chat.di

import com.ubuntuyouiwe.chat.data.repository.AuthRepositoryImpl
import com.ubuntuyouiwe.chat.data.repository.MessagingRepositoryImpl
import com.ubuntuyouiwe.chat.data.source.remote.firebase.auth.AuthDataSource
import com.ubuntuyouiwe.chat.data.source.remote.firebase.firestore.FireStoreDataSource
import com.ubuntuyouiwe.chat.domain.repository.AuthRepository
import com.ubuntuyouiwe.chat.domain.repository.MessagingRepository
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
        authentication: AuthDataSource,
    ): AuthRepository =
        AuthRepositoryImpl(authentication)

    @Provides
    @Singleton
    fun provideMessagingRepository(
        fireStoreDataSource: FireStoreDataSource,
    ): MessagingRepository =
        MessagingRepositoryImpl(fireStoreDataSource)


}