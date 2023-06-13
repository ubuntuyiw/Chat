package com.ubuntuyouiwe.chat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ubuntuyouiwe.chat.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.chat.ui.theme.ChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
                NavHostScreen()
            }
        }
    }
}
