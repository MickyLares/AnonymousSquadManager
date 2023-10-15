package com.miguellares.anonymoussquadmanager.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.Login.LoginViewModel

@Composable
fun topBarInterior(navController: NavController, sectionName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigate(AppScreen.MainScreen.route) }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Volver",
            modifier = Modifier.padding(start = 4.dp, top = 10.dp, bottom = 10.dp, end = 29.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = sectionName ?: "", modifier = Modifier.padding(
                start = 4.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun topBarResetPassword(loginViewModel: LoginViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(56.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { loginViewModel.onCLickForgetPassword(false) }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
        }

    }
}
@Composable
fun topBarInteriorExitFile(navController: NavController, sectionName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { DrawerMenuItem.GestionEquipo.route?.let { navController.navigate(it) } }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Volver",
            modifier = Modifier.padding(start = 4.dp, top = 10.dp, bottom = 10.dp, end = 29.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = sectionName ?: "", modifier = Modifier.padding(
                start = 4.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}