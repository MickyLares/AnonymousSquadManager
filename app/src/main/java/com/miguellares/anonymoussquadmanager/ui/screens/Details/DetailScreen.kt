package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInterior
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.Details.DetailScreenViewModel
import com.miguellares.anonymoussquadmanager.utils.Utils

@Composable
fun DetailOficialScreen(
    partida: Partida,
    navController: NavController,
    usuario: Usuario,
    viewModel: DetailScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val onClickButtonInvited = remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = { topBarInterior(navController = navController, "Partida del ${partida.fecha}") }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            renderOficialHeaderBody()
            mySpace(espacio = 20)
            renderBody(usuario = usuario, partida = partida, onClickButtonInvited, viewModel)
        }

    }
}

@Composable
fun DetailNoOficialScreen(
    partida: Partida,
    navController: NavController,
    usuario: Usuario,
    viewModel: DetailScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val onClickButtonInvited = remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = { topBarInterior(navController = navController, "Partida del ${partida.fecha}") }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            renderNoOficialHeaderBody(usuario, partida)
            mySpace(espacio = 20)
            renderBody(usuario = usuario, partida = partida, onClickButtonInvited, viewModel)
        }

    }
}

@Composable
fun renderBody(
    usuario: Usuario,
    partida: Partida,
    onClickButtonInvited: MutableState<Boolean>,
    viewModel: DetailScreenViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            bindTitleEvents(title = "Jugadores", size = 16)
            partida.jugadores.forEach {
                jugadores(Utils.PARTIDA, name = it, partida)
            }
            mySpace(espacio = 5)
            bindTitleEvents(title = "Campo", size = 16)
            campoField(name = partida.campo)
            mySpace(espacio = 5)
            bindTitleEvents(title = "Fecha", size = 16)
            campoField(name = partida.fecha)
            renderButtonInvite(onClickButtonInvited)
            mySpace(espacio = 5)
            val agregado = true
            if (!agregado) {
                renderButtonInscribirme()
            } else {
                renderButtonRemove()
            }
            if (onClickButtonInvited.value) {
                DialogInvited(
                    openDialog = onClickButtonInvited,
                    usuario = usuario,
                    partida = partida
                )
            }
        }

    }
}

@Composable
fun renderButtonRemove() {
    Button(
        onClick = { },
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Image(
            imageVector = Icons.Default.Remove,
            contentDescription = "Borrarme"
        )
        Text(
            text = "Borrarme de partida",
            modifier = Modifier.padding(horizontal = 5.dp),
            color = colorResource(
                id = R.color.black
            )
        )
    }
}

@Composable
fun renderButtonInscribirme() {
    Button(
        onClick = { },
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregarme"
        )
        Text(
            text = "Añadirme a partida",
            modifier = Modifier.padding(horizontal = 5.dp),
            color = colorResource(
                id = R.color.black
            )
        )
    }
}

@Composable
fun renderButtonInvite(onClickButtonInvited: MutableState<Boolean>) {

    Button(
        onClick = { onClickButtonInvited.value = true }, modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Image(
            imageVector = Icons.Default.PersonAddAlt,
            contentDescription = "Invitar"
        )
        Text(
            text = "Agregar Invitado",
            modifier = Modifier.padding(horizontal = 5.dp),
            color = colorResource(
                id = R.color.black
            )
        )
    }
}

@Composable
fun jugadores(
    from: String,
    name: String,
    partida: Partida,
    detailScreenViewModel: DetailScreenViewModel = viewModel()
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name, modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .weight(1f)

        )
        if (name.contains("(inv)-")) {
            IconButton(
                onClick = {
                    detailScreenViewModel.clickOnInvitedName(
                        from,
                        name,
                        partida = partida
                    )
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")

            }

        }

    }

}

@Composable
fun campoField(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = name, modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
    }

}

@Composable
fun renderOficialHeaderBody() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = colorResource(id = R.color.black)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.anonymous),
                contentDescription = "logo Anonymous"

            )
        }

    }

}

@Composable
fun renderNoOficialHeaderBody(usuario: Usuario, partida: Partida) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(color = colorResource(id = R.color.white))
            .border(1.dp, color = colorResource(id = R.color.black_darkness)),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.flag_of_cross_of_burgundy_svg),
                contentDescription = "logo Anonymous",
                contentScale = ContentScale.Crop
            )
        }


    }

}