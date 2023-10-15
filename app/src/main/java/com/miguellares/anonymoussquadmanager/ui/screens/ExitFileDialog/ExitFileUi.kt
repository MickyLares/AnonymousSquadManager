package com.miguellares.anonymoussquadmanager.ui.screens.ExitFileDialog

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.SquadManager.SquadManagerViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace

@Composable
fun ExitFileDialogo(usuario: Usuario, mailUser: String, viewModel: SquadManagerViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 20.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { viewModel.onClickExitFile(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            ExitFileUi(viewModel = viewModel, usuario = usuario, mailUser = mailUser)
        }
    }
}

@Composable
fun ExitFileUi(viewModel: SquadManagerViewModel, usuario: Usuario, mailUser: String) {
    val motivo by viewModel.motivo.observeAsState(initial = "")
    val isValidMotivo by viewModel.isValidMotivo.observeAsState(initial = false)
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
            bindTitleEvents(title = "Expediente de Expulsión", size = 17)
            mySpace(espacio = 5)
            renderDetails(usuario, mailUser)
            mySpace(espacio = 5)
            renderMotivo(
                motivo,
                { viewModel.onChangeMotivo(it) },
                isValidMotivo
            )
            renderButtonsExitFile(isValidMotivo, usuario, mailUser, motivo, viewModel)
        }
    }

}

@Composable
fun renderButtonsExitFile(
    isvalidMotivo: Boolean,
    usuario: Usuario,
    mailUser: String,
    motivo: String,
    viewModel: SquadManagerViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { viewModel.onClickExitFile(false) },
            modifier = Modifier
                .padding(end = 5.dp)
                .height(56.dp)
        ) {
            Text(text = "Cerrar", color = colorResource(id = R.color.VerdeMilitar))
        }
        mySpace(espacio = 5)
        Button(
            onClick = {
                viewModel.openFileToRemoveUser(usuario = usuario, motivo, mailUser)
                viewModel.onClickExitFile(false)
            },
            modifier = Modifier
                .padding(end = 5.dp)
                .height(56.dp)
        ) {
            Text(text = "Abrir Expediente", color = colorResource(id = R.color.VerdeMilitar))
        }
    }
}

@Composable
fun renderMotivo(
    motivo: String,
    onChangeMotivo: (String) -> Unit,
    validMotivo: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = motivo,
            onValueChange = onChangeMotivo,
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp),
            label = { Text(text = "Motivo de expulsión") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 140,
            singleLine = false,
            colors = if (validMotivo) {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.YellowAnonymous),
                    focusedLabelColor = colorResource(id = R.color.black_darkness)
                )
            } else {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.RedAnonymous),
                    focusedLabelColor = colorResource(id = R.color.RedAnonymous)
                )
            }
        )
    }
}

@Composable
fun renderDetails(usuario: Usuario, mailUser: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Text(text = "Expedientado: ${usuario.nickName}")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Text(text = "Cantidad de partidas asistidas: ${usuario.partidas?.size}")
        }
    }

}
