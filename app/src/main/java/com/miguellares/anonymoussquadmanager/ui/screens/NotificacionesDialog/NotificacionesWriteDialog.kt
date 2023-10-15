package com.miguellares.anonymoussquadmanager.ui.screens.NotificacionesDialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.ui.screens.SquadManager.SquadManagerViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace
import com.miguellares.anonymoussquadmanager.utils.Utils

@Composable
fun NotificacionesWriteUI(viewModel: SquadManagerViewModel) {
    val message by viewModel.message.observeAsState(initial = "")
    val isValidMessage by viewModel.isValidMessage.observeAsState(initial = false)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.VerdeMilitar)),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            renderTitle()
            mySpace(espacio = 5)
            renderMessage(
                message,
                messageChange = { viewModel.onChangeMessage(it) },
                isValidMessage
            )
            mySpace(espacio = 5)
            renderRowButtonsNotifications(isValidMessage, message, viewModel)
        }
    }
}

@Composable
fun renderRowButtonsNotifications(
    isValidMessage: Boolean,
    message: String,
    viewModel: SquadManagerViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            viewModel.sendNotificacion(message)
            viewModel.onClickOpenNotificationUI(false)
        }, enabled = isValidMessage, modifier = Modifier.padding(end = 10.dp)) {
            Text(text = "Enviar")
        }
        Button(
            onClick = { viewModel.onClickOpenNotificationUI(false) },
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(text = "Cancelar")
        }
    }
}

@Composable
fun renderMessage(
    message: String,
    messageChange: (String) -> Unit,
    isValidMessage: Boolean
) {
    OutlinedTextField(
        value = message,
        onValueChange = messageChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        maxLines = 140,
        label = { Text(text = "Mensaje a enviar hasta 140 lineas") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = if (isValidMessage) {
            TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.black_darkness)
            )
        } else {
            TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = R.color.RedAnonymous)
            )
        }
    )
}

@Composable
fun renderTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = Utils.AVISO, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}
