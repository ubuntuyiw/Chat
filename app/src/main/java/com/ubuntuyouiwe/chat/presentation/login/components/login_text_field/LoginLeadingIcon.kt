package com.ubuntuyouiwe.chat.presentation.login.components.login_text_field

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LoginLeadingIcon(imageVector: ImageVector) {
    Icon(
        imageVector = imageVector,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.primary
    )
}