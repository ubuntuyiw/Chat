package com.ubuntuyouiwe.chat.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ubuntuyouiwe.chat.presentation.components.DialogButton
import com.ubuntuyouiwe.chat.presentation.components.dialog.DialogTitleText
import com.ubuntuyouiwe.chat.presentation.components.dialog.text_field.Label

@Composable
fun CreateRoomDialog(
    chatRoomName: String,
    chatRoomNameChange: (String) -> Unit,
    checkAlertDialogChange: (Boolean) -> Unit,
    createRoomClick: () -> Unit,
    checkBoxAI: Boolean,
    checkBoxAIdChange: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = {
        checkAlertDialogChange(false)
    }) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            )

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                DialogTitleText(
                    text = "Create Room",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Start),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                TextField(
                    value = chatRoomName,
                    onValueChange = chatRoomNameChange,
                    label = { Label("Name") })


            }
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Checkbox(
                    checked = checkBoxAI ,
                    onCheckedChange = {checkBoxAIdChange(!checkBoxAI)})
                Text(text = "Just AI Chat", fontWeight = FontWeight.Medium, fontSize = 13.sp)
            }



            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {

                DialogButton(onClick = { checkAlertDialogChange(false) }, text = "Cancel")

                Spacer(modifier = Modifier.padding(8.dp))

                DialogButton(createRoomClick, "Create")

            }

            Spacer(modifier = Modifier.padding(8.dp))

        }


    }
}