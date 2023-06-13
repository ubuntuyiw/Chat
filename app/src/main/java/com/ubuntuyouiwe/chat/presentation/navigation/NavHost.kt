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
fun NavHostScreen(viewModel: NavHostViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    val state by viewModel.stateAuth.collectAsStateWithLifecycle()


    val startDestination = if (state.isLoading) Screen.SPLASH.name
    else if (state.success != null) Screen.HOME.name
    else Screen.LOGIN.name



    NavHost(navController = navController, startDestination = startDestination) {
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