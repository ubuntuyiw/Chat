package com.ubuntuyouiwe.chat.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ubuntuyouiwe.chat.domain.repository.NotificationRepository
import com.ubuntuyouiwe.chat.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.chat.presentation.navigation.Screen
import com.ubuntuyouiwe.chat.ui.theme.ChatTheme
import com.ubuntuyouiwe.chat.util.notification_channel.NotificationOnEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var notificationRepository: NotificationRepository


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
                val viewModel: MainActivityViewModel = hiltViewModel()
                val state by viewModel.stateAuth.collectAsStateWithLifecycle()
                val startDestination = if (state.isLoading) Screen.SPLASH
                else if (state.success != null) Screen.CHAT
                else Screen.LOGIN
                installSplashScreen().apply {
                    this.setKeepOnScreenCondition {
                        state.isLoading
                    }
                }

                NavHostScreen(startDestination = startDestination)
            }

        }

        if (NotificationOnEvent.OPEN.actionName == intent.getStringExtra(NotificationOnEvent.OPEN.actionName)) {
            notificationRepository.messages.clear()
        }
    }


}
