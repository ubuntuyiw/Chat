package com.ubuntuyouiwe.chat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.chat.presentation.SplashScreen
import com.ubuntuyouiwe.chat.presentation.chat.ChatScreen
import com.ubuntuyouiwe.chat.presentation.login.Login

@Composable
fun NavHostScreen(startDestination: Screen) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Screen.SPLASH.name) {
            SplashScreen()
        }

        composable(Screen.LOGIN.name) {
            Login()
        }

        composable(Screen.CHAT.name) {
            ChatScreen()
        }
    }

}