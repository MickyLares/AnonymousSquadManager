package com.miguellares.anonymoussquadmanager.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TopBar(
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    renderTopBar(usuario, viewModel, navController, scaffoldState, scope)
}

@Composable
fun renderTopBar(
    usuario: Usuario?,
    viewModel: MainViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    var name = usuario?.nickName ?: "Default"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(5.dp)
            .background(color = colorResource(id = R.color.black_darkness))
    ) {
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Card(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(4.dp)
                .clickable { scope.launch { scaffoldState.drawerState.open() } },
            shape = RoundedCornerShape(90.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "Bienvendio $name",
            modifier = Modifier
                .padding(10.dp)
                .weight(3f),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.white)
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))

        IconButton(onClick = { viewModel.onClickExit(true) }) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Close Sesión",
                tint = colorResource(
                    id = R.color.white
                )
            )
        }
    }
}
