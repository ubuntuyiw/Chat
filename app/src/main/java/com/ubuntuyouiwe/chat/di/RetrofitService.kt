package com.ubuntuyouiwe.chat.di

import com.ubuntuyouiwe.chat.data.source.remote.chatgpt.OpenAIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitService {

    @Singleton
    @Provides
    fun provideAuthenticationApi(retrofit: Retrofit): OpenAIService =
        retrofit.create(OpenAIService::class.java)
}