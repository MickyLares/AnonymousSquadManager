package com.miguellares.anonymoussquadmanager

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.models.PreferenceManager
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.Login.LoginViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.resetPasswordDialogo

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    context: Context
) {

    val email by loginViewModel.email.observeAsState(initial = "")
    val isValidEmail by loginViewModel.isValidEmail.observeAsState(initial = false)
    val password by loginViewModel.password.observeAsState(initial = "")
    val isValidPassword by loginViewModel.isValidPassword.observeAsState(initial = false)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val onClickForgetPassword by loginViewModel.onClickForgetPassword.observeAsState(initial = false)

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.black),
                            colorResource(id = R.color.white)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(90.dp), border = BorderStroke(
                    2.dp, color = colorResource(
                        id = R.color.black
                    )
                ),
                elevation = 2.dp
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                    contentDescription = "",
                    Modifier
                        .width(130.dp)
                        .height(130.dp),
                    Alignment.Center

                )
            }
        }
        renderEmailText(
            email = email,
            emailsChange = {
                loginViewModel.onEmailLoginChanged(it)
            },
            isValidEmail = isValidEmail
        )
        renderPasswordText(
            password = password,
            passwordChange = {
                loginViewModel.onPasswordLoginChanged(it)
            },
            isVisiblePassword = passwordVisible,
            passwordVisibleChange = {
                passwordVisible = !passwordVisible
            },
            isValidPassword = isValidPassword
        )

        if (onClickForgetPassword) {
            resetPasswordDialogo(loginViewModel = loginViewModel)
        }

        renderButtonLogin(
            isValidEmail,
            isValidPassword,
            email,
            password,
            navController,
            loginViewModel,
            context
        )
        renderForgetPassword(loginViewModel)

    }
}

@Composable
fun renderOnRemember(onClickRemember: Boolean, onClickChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = onClickRemember, onCheckedChange = onClickChecked)
        Text(text = "Recordar Usuario y Contraseña ")
    }


}

@Composable
fun renderForgetPassword(loginViewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = { loginViewModel.onCLickForgetPassword(true) },
            modifier = Modifier.padding(horizontal = 54.dp, vertical = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.VerdeMilitar),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            bindTitleEvents(title = "Haz click si olvidaste tu contraseña", size = 8)
        }
    }


}

@Composable
fun renderButtonLogin(
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    email: String,
    password: String,
    navController: NavController,
    viewModel: LoginViewModel,
    context: Context
) {
    val isLoading = viewModel.loading.observeAsState()
    val buttonColor = ButtonDefaults.buttonColors(
        colorResource(id = R.color.YellowAnonymous),
        contentColor = MaterialTheme.colors.onSurface
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(59.dp),
        Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.signWithEmailandPassword(
                    email = email,
                    password = password,
                    home = {
                        viewModel.getUserbyEmail(email, password) {
                            showError(context, "has sido expulsado el Escuadrón Anonymous")
                        }
                        navController.navigate(AppScreen.MainScreen.route)
                    }
                ) {
                    showError(context, "No se ha encontrado el Usuario")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidEmail && isValidPassword,
            colors = buttonColor
        ) {
            Text(text = "Iniciar Sesión", color = colorResource(id = R.color.black_darkness))
        }
    }
}

fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun renderPasswordText(
    password: String,
    passwordChange: (String) -> Unit,
    isVisiblePassword: Boolean,
    passwordVisibleChange: () -> Unit,
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
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isVisiblePassword) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = passwordVisibleChange) {
                    Icon(imageVector = image, contentDescription = "")
                }
            },
            visualTransformation = if (isVisiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            colors = if (isValidPassword) {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.YellowAnonymous),
                    focusedLabelColor = colorResource(id = R.color.black)
                )
            } else {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = colorResource(id = R.color.RedAnonymous),
                    focusedBorderColor = colorResource(id = R.color.RedAnonymous)
                )
            }

        )
    }
}

@Composable
fun renderEmailText(
    email: String,
    emailsChange: (String) -> Unit,
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
            onValueChange = emailsChange,
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if (isValidEmail) {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = colorResource(id = R.color.black),
                    focusedBorderColor = colorResource(id = R.color.YellowAnonymous)
                )
            } else {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = colorResource(id = R.color.RedAnonymous),
                    focusedBorderColor = colorResource(id = R.color.RedAnonymous)

                )

            }
        )
    }
}
