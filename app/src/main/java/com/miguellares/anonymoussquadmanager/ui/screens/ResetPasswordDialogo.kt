package com.miguellares.anonymoussquadmanager.ui.screens

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
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarResetPassword
import com.miguellares.anonymoussquadmanager.ui.screens.Login.LoginViewModel

@Composable
fun resetPasswordDialogo(loginViewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { loginViewModel.onCLickForgetPassword(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            DialogoResetScreen(loginViewModel = loginViewModel)
        }
    }
}

@Composable
fun DialogoResetScreen(loginViewModel: LoginViewModel) {
    val email by loginViewModel.email.observeAsState(initial = "")
    val isValidEmail by loginViewModel.isValidEmail.observeAsState(initial = false)
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 4.dp, vertical = 4.dp),
        topBar = { topBarResetPassword(loginViewModel = loginViewModel) }
    ) {
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
                bindTitleEvents(title = "Ingresa tu email para recibir tu enlace", size = 15)
                mySpace(espacio = 5)
                renderRowEmail(
                    email = email,
                    onEmailChange = {
                        loginViewModel.onEmailLoginChanged(it)
                    },
                    isValidEmail = isValidEmail
                )
                mySpace(espacio = 5)

                renderButtonAceptar(isValidEmail) {
                    loginViewModel.sendEmailWithLinkPassword(email)
                }
                mySpace(espacio = 5)

            }
        }
    }
}

@Composable
fun renderButtonAceptar(isValidEmail: Boolean, onClickAceptar: () -> Unit) {
    Button(
        onClick = { onClickAceptar() },
        enabled = isValidEmail,
        modifier = Modifier.padding(horizontal = 54.dp)
    ) {
        bindTitleEvents(title = "Aceptar", size = 9)
    }
}

@Composable
fun renderRowEmail(
    email: String,
    onEmailChange: (String) -> Unit,
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
            onValueChange = onEmailChange,
            label = { Text(text = "Tu Email") },
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
