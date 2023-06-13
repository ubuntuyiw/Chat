package com.ubuntuyouiwe.chat.presentation.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SpecialSnackBar(
    hostState: SnackbarHostState,
    isLoading: Boolean,
    errorMessage: String,
    success: String
) {
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