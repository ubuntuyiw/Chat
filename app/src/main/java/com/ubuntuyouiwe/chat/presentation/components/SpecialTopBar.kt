package com.ubuntuyouiwe.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialTopBar(title: String, chatGptState: Boolean, isFromCache: Boolean? = false, signOutClick: () -> Unit) {
    var dropdownMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = title)
                Spacer(modifier = Modifier.padding(16.dp))
                if (isFromCache == true) {
                    Text(text = "Syncing...", fontSize = 15.sp)
                }
                if (chatGptState)
                    Text(text = "AI yazıyor...")
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(onClick = {
                dropdownMenu = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "MoreVert",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                DropdownMenu(expanded = dropdownMenu, onDismissRequest = { dropdownMenu = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Sign Out")
                        }, onClick = signOutClick
                    )
                }
            }


        }
    )
}