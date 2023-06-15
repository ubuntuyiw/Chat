package com.ubuntuyouiwe.chat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.chat.presentation.chat.Chat
import com.ubuntuyouiwe.chat.presentation.login.Login

@Composable
fun NavHostScreen(startDestination: Screen) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Screen.SPLASH.name) {

        }

        composable(Screen.LOGIN.name) {
            Login(navController)
        }

        composable(Screen.HOME.name) {
            Chat()
        }
    }

}