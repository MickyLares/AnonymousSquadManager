package com.miguellares.anonymoussquadmanager.ui.screens.ExitDialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace

@Composable
fun ExitDialog(viewModel: MainViewModel, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { viewModel.onClickExit(false) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            DialogoExit(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun DialogoExit(viewModel: MainViewModel, navController: NavController) {
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
            bindTitleEvents(title = "Salir de la aplicación", size = 16)
            mySpace(espacio = 10)
            bindTitleEvents(title = "¿Estas Seguro que deseas Salir?", size = 14)
            mySpace(espacio = 5)
            renderButtonDialogoExit(viewModel, navController)
        }
    }
}

@Composable
fun renderButtonDialogoExit(viewModel: MainViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = {
            viewModel.onClickExit(false)
            viewModel.logoutUser(navController)

        }) {
            bindTitleEvents(title = "Salir", size = 10)
        }
        Button(onClick = { viewModel.onClickExit(false) }) {
            bindTitleEvents(title = "Cancelar", size = 10)
        }
    }
}