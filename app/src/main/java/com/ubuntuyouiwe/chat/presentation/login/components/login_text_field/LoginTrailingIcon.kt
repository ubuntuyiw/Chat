package com.ubuntuyouiwe.chat.presentation.login.components.login_text_field

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ubuntuyouiwe.chat.R

@Composable
fun LoginTrailingIcon(passwordShow: Boolean, passwordShowChange: (Boolean) -> Unit) {
    if (passwordShow) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_key_off_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                passwordShowChange(false)
            }
        )

    } else {
        Icon(
            painter = painterResource(id = R.drawable.baseline_key_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                passwordShowChange(true)
            }
        )

    }
}