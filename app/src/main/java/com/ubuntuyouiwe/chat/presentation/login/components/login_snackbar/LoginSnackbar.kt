package com.ubuntuyouiwe.chat.presentation.login.components.login_snackbar

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun LoginSnackbar(
    isLoading: Boolean = false,
    errorMessage: String = "",
    success: String = ""
) {
    val hostState = remember { SnackbarHostState() }

    SnackbarHost(hostState) { data ->
        Snackbar(data)
    }

    if (isLoading) {
        LaunchedEffect(key1 = Unit) {
            hostState.showSnackbar("Loading...", duration = SnackbarDuration.Indefinite)
        }
    } else if (errorMessage.isNotBlank()) {
        LaunchedEffect(key1 = errorMessage) {
            hostState.showSnackbar(errorMessage)
        }
    } else if (success.isNotBlank()) {
        LaunchedEffect(key1 = success) {
            hostState.showSnackbar(success)
        }
    }

}