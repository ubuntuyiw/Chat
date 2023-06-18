package com.ubuntuyouiwe.chat.presentation.components

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.chat.R

@Composable
fun MessageInputBox(
    value: String,
    onValueChange: (value: String) -> Unit,
    send: () -> Unit
) {

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        SpecialTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            maxLines = 5,
            placeholder = {
                Text(
                    "Type a message",
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(32.dp),
            trailingIcon = {
                val context = LocalContext.current
                IconButton(onClick = {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    context.startActivity(takePictureIntent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_camera_24),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }


            },
            modifier = Modifier.weight(1f)


        )
        Button(
            onClick = send,
            modifier = Modifier
                .padding(start = 8.dp)
                .widthIn(min = 52.dp)
                .heightIn(min = 52.dp)
                .align(Alignment.Bottom),

            ) {
            Icon(Icons.Default.Send, contentDescription = "Send message")
        }
    }


}