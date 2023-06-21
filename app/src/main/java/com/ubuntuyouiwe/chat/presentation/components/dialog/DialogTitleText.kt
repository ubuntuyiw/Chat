package com.ubuntuyouiwe.chat.presentation.components.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DialogTitleText(text: String, modifier: Modifier) {
    Text(
        text = text, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = modifier
    )
}