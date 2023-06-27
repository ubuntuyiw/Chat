package com.ubuntuyouiwe.chat.di.activity_module

import android.app.Activity
import com.ubuntuyouiwe.chat.presentation.permission_handler.PermissionHandler
import com.ubuntuyouiwe.chat.presentation.permission_handler.PermissionHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object PermissionModule {

    @Provides
    fun providePermissionHandler(activity: Activity): PermissionHandler =
        PermissionHandlerImpl(activity)
}