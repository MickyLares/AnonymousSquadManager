package com.miguellares.anonymoussquadmanager.ui.screens.EditPerfilDialogo

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.PerfilScreen.PerfilScreenViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace

@Composable
fun EditPerfilDialogoScreen(
    navController: NavController,
    viewModel: PerfilScreenViewModel,
    usuario: Usuario
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { viewModel.onClickEdit(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            EditDialogo(viewModel = viewModel, navController = navController, usuario)
        }
    }
}

@Composable
fun EditDialogo(viewModel: PerfilScreenViewModel, navController: NavController, usuario: Usuario) {
    val nickName1 by viewModel.nickName.observeAsState(initial = usuario.nickName ?: "")
    val isValidNickName by viewModel.isValidNickName.observeAsState(initial = false)
    val password1 by viewModel.password1.observeAsState(initial = "")
    val isValidPassword by viewModel.isValidPassword.observeAsState(initial = false)
    val reinicio by viewModel.reinicio.observeAsState(initial = false)
    val tiempo by viewModel.tiempo.observeAsState(initial = 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(shape = RoundedCornerShape(6.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.white))
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            bindTitleEvents(title = "Editar perfil de Usuario", size = 15)
            mySpace(espacio = 5)
            renderPasswordEdit(
                password = password1,
                isValidPassword = isValidPassword,
                onPasswordChange = { viewModel.onPasswordChange(it) }
            )
            if (reinicio) {
                showTextReinicio(tiempo, navController, viewModel)
            }
            renderButtonEditPerfil(
                validNickName = isValidNickName,
                validPassword = isValidPassword,
                onClickAceptar = {
                    viewModel.editUserData(usuario, nickName1, password1)
                    val time = Contador(navController, viewModel, 10000, 1000)
                    time.start()

                },
                onClickCancel = {
                    viewModel.onClickEdit(false)
                })
        }
    }
}

@Composable
fun showTextReinicio(tiempo: Long, navController: NavController, viewModel: PerfilScreenViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(
                color = colorResource(
                    id = R.color.white
                ),
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Column() {

            bindTitleEvents(title = "Se va a Cerrar tu Sesión para actualizar los datos", size = 14)
            bindTitleEvents(title = "$tiempo Seg.", size = 9)
        }


    }
}

@Composable
fun renderButtonEditPerfil(
    validNickName: Boolean,
    validPassword: Boolean,
    onClickAceptar: () -> Unit,
    onClickCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onClickCancel() }, modifier = Modifier.padding(end = 4.dp)) {
            Text(text = "Cancel")
        }
        Button(
            onClick = { onClickAceptar() },
            modifier = Modifier.padding(start = 4.dp),
            enabled = validPassword
        ) {
            Text(text = "Aceptar")
        }
    }
}

@Composable
fun renderPasswordEdit(
    password: String,
    isValidPassword: Boolean,
    onPasswordChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            singleLine = true,
            colors = if (isValidPassword) {
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
fun renderNickEdit(
    nickName: String,
    onNickNameChange: (String) -> Unit,
    isValidNickName: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nickName,
            onValueChange = onNickNameChange,
            label = { Text(text = "Nickname del Operador") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if (isValidNickName) {
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

class Contador(
    val navController: NavController,
    val viewModel: PerfilScreenViewModel,
    millisInFuture: Long,
    countDownInterval: Long
) : CountDownTimer(millisInFuture, countDownInterval) {
    override fun onTick(p0: Long) {
        viewModel.changeTime(p0 / 1000)
    }

    override fun onFinish() {
        viewModel.logoutUserPerfil(navController)
    }

}