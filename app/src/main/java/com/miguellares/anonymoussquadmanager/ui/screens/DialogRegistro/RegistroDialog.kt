package com.miguellares.anonymoussquadmanager.ui.screens.DialogRegistro

import androidx.compose.foundation.BorderStroke
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
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.SquadManager.SquadManagerViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace

@Composable
fun RegistroScreenDialog(
    navController: NavController,
    viewModel: SquadManagerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { viewModel.onClickShowRegistro(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            RegistroDialogo(viewModel, navController)
        }
    }
}

@Composable
fun RegistroDialogo(viewModel: SquadManagerViewModel, navController: NavController) {
    val email by viewModel.email.observeAsState(initial = "")
    val isValidEmail by viewModel.isValidEmail.observeAsState(initial = false)
    val password by viewModel.password.observeAsState(initial = "")
    val isValidPassword by viewModel.isValidPassword.observeAsState(initial = false)
    val nickName by viewModel.nickName.observeAsState(initial = "")
    val isValidNickName by viewModel.isValidNickname.observeAsState(initial = false)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.white))
            .padding(horizontal = 24.dp, vertical = 24.dp),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.VerdeMilitar)),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            bindTitleEvents(title = "Registro de Usuarios", size = 15)
            mySpace(espacio = 5)
            renderEmailRegistro(
                email = email,
                emailOnChange = { viewModel.onChangeEmailRegistro(it) },
                isValidEmail = isValidEmail
            )
            renderPasswordRegistro(
                password = password,
                passwordOnChange = { viewModel.onPasswordRegistroChange(it) },
                isValidPassword = isValidPassword
            )
            renderNickRegistro(
                nickName = nickName,
                onChangeNickName = { viewModel.onChangeNicknameRegistro(it) },
                isValidNickName = isValidNickName
            )
            renderButtons(
                viewModel,
                email,
                isValidEmail,
                password,
                isValidPassword,
                nickName,
                isValidNickName,
                navController
            )
        }
    }
}

@Composable
fun renderNickRegistro(
    nickName: String,
    onChangeNickName: (String) -> Unit,
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
            onValueChange = onChangeNickName,
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

@Composable
fun renderButtons(
    viewModel: SquadManagerViewModel,
    email: String,
    isValidEmail: Boolean,
    password: String,
    isValidPassword: Boolean,
    nickName: String,
    isValidNickName: Boolean,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { viewModel.onClickShowRegistro(false) },
            modifier = Modifier
                .padding(end = 5.dp)
                .height(56.dp)
        ) {
            Text(text = "Cerrar", color = colorResource(id = R.color.VerdeMilitar))
        }
        mySpace(espacio = 5)
        Button(
            onClick = {
                viewModel.crearUsuario(email = email, password = password, nickName = nickName)
                navController.navigate(AppScreen.MainScreen.route)
            },
            modifier = Modifier
                .padding(start = 5.dp)
                .height(56.dp),
            enabled = isValidEmail && isValidPassword && isValidNickName
        ) {
            Text(text = "Añadir", color = colorResource(id = R.color.VerdeMilitar))
        }
    }
}

@Composable
fun renderEmailRegistro(
    email: String,
    emailOnChange: (String) -> Unit,
    isValidEmail: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = emailOnChange,
            label = { Text(text = "Email del Operador") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if (isValidEmail) {
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
fun renderPasswordRegistro(
    password: String,
    passwordOnChange: (String) -> Unit,
    isValidPassword: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = passwordOnChange,
            label = { Text(text = "Password del Operador") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
