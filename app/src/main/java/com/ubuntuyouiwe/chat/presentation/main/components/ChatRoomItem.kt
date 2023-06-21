package com.ubuntuyouiwe.chat.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ubuntuyouiwe.chat.R

@Composable
fun ChatRoomItem(
    cardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .heightIn(min = 62.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {

        Column(
            modifier = Modifier.clickable(onClick = cardClick)
        ) {
            Row {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(62.dp)
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chat_logo),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Spacer(modifier = Modifier.padding(end = 8.dp))

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Deneme", fontWeight = FontWeight.Medium)
                        Text(
                            text = "12.00",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                    }

                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Hiç mesaj yok", fontSize = 13.sp)
                }
            }


        }

    }
    Spacer(modifier = Modifier.padding(bottom = 16.dp))
}