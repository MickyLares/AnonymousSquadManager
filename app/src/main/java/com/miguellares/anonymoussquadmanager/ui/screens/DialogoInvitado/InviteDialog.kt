package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.Details.DetailScreenViewModel

@Composable
fun DialogInvited(
    openDialog: MutableState<Boolean>,
    usuario: Usuario,
    partida: Partida,
    viewModel: DetailScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val nameInvite by viewModel.nameInvited.observeAsState(initial = "")
    Dialog(
        onDismissRequest = { openDialog.value = false },
        properties = DialogProperties(dismissOnBackPress = false)
    ) {
        InvitedBodyDialog(openDialog, viewModel, usuario, partida, nameInvite)
    }
}

@Composable
fun InvitedBodyDialog(
    openDialog: MutableState<Boolean>,
    viewModel: DetailScreenViewModel,
    usuario: Usuario,
    partida: Partida,
    nameInvite: String
) {
    renderInvitedRow(
        openDialog = openDialog,
        usuario = usuario,
        partida = partida,
        nameInvited = nameInvite,
        viewModel
    ) {
        viewModel.onNameInvitedChange(it)
    }
}

@Composable
fun renderInvitedRow(
    openDialog: MutableState<Boolean>,
    usuario: Usuario,
    partida: Partida,
    nameInvited: String,
    viewModel: DetailScreenViewModel,
    onChangeName: (String) -> Unit
) {
    Card(modifier = Modifier.height(190.dp)) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .background(color = colorResource(id = R.color.white)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = nameInvited,
                    onValueChange = onChangeName,
                    modifier = Modifier.padding(10.dp),
                    label = { Text(text = "Invitado") }
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .background(color = colorResource(id = R.color.white))
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { openDialog.value = false },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Cerrar",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = colorResource(
                            id = R.color.black_darkness
                        )
                    )
                }
                Button(
                    onClick = {
                        viewModel.addInvited("(inv)-$nameInvited", partida, usuario)
                        viewModel.onNameInvitedChange("")
                        openDialog.value = false
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Añadir Invitado",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = colorResource(
                            id = R.color.black_darkness
                        )
                    )
                }
            }
        }
    }

}