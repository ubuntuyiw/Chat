package com.ubuntuyouiwe.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.ubuntuyouiwe.chat.R

@Composable
fun Message(messageOwner: Boolean, email: String, content: String, hasPendingWrites: Boolean, date: String?) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        val localDensity = LocalConfiguration.current.screenWidthDp

        Card(
            modifier = Modifier
                .heightIn(20.dp)
                .widthIn(0.dp, (localDensity * 0.8f).dp)
                .align(if (messageOwner) Alignment.BottomEnd else Alignment.BottomStart),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {

            Column(modifier = Modifier.padding(8.dp)) {
                if (!messageOwner)
                    Text(
                        text = email,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = content,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            bottom = 4.dp,
                            start = 4.dp,
                            end = 64.dp
                        )
                    )

                    Text(
                        text = date?: "",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.BottomEnd)
                    )

                    if (messageOwner) {
                        if (hasPendingWrites)
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_access_time_24),
                                contentDescription = "Sending",
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        else {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_done_24),
                                contentDescription = "Sending",
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }


                }


            }
        }
    }
}