package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.HomeBannerView
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.EditPerfilDialogo.EditPerfilDialogoScreen
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.PerfilScreen.PerfilScreenViewModel

@Composable
fun PerfilScreen(
    usuario: Usuario,
    navController: NavController,
    listaPartidas: List<Partida> = emptyList(),
    partidasEspeciales: List<Partida> = emptyList(),
    entrenamientos: List<Partida> = emptyList(),
    viewModel: PerfilScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModelMain: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    renderBodyDialog(
        viewModel,
        viewModelMain,
        usuario,
        navController,
        listaPartidas,
        partidasEspeciales,
        entrenamientos
    )


}

@Composable
private fun renderBodyDialog(
    viewModel: PerfilScreenViewModel,
    viewModelMain: MainViewModel,
    usuario: Usuario,
    navController: NavController,
    listaPartidas: List<Partida> = emptyList(),
    partidasEspeciales: List<Partida> = emptyList(),
    entrenamientos: List<Partida> = emptyList()
) {
    val nickName by viewModel.nickName.observeAsState(initial = usuario.nickName ?: "")
    val password by viewModel.password.observeAsState(initial = "")
    val clickEdit by viewModel.onClickEdit.observeAsState(initial = false)
    viewModel.setDataUsuario(usuario)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (clickEdit) {
                EditPerfilDialogoScreen(navController, viewModel, usuario)
            }
            mySpace(espacio = 10)
            Text(text = "Mi Perfil", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Image(
                painter = painterResource(id = R.drawable.no_event_solider),
                contentDescription = "foto Perfil",
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .clip(shape = CircleShape)
                    .border(
                        width = 1.dp, colorResource(id = R.color.VerdeMilitar), shape = CircleShape
                    )
            )
            mySpace(espacio = 20)

            renderNickName(nickName, viewModel)
            renderPassword(password = password, viewModel)
            renderPartidas(listaPartidas, usuario)
            renderPartidasEspeciales(
                listaPartidas = partidasEspeciales, usuario = usuario
            )
            renderEntrenamientos(listaPartidas = entrenamientos, usuario = usuario)
            renderButtonNavigations(
                navController = navController,
                usuario = usuario,
                nickname = nickName,
                password = password,
                viewModelMain = viewModelMain
            )
            HomeBannerView(padding = 40)

        }
    }
}

@Composable
fun renderPartidas(listaPartidas: List<Partida> = emptyList(), usuario: Usuario) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 4.dp, end = 72.dp, top = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var cont = contadorPartidasUsuario(listaPartidas, usuario)
        var contPartidas = contadorPartidasOficiales(listaPartidas)
        Image(
            imageVector = Icons.Default.Games,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "Cantidad de partidas: ${cont} de ${contPartidas}",
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp, color = colorResource(id = R.color.VerdeMilitar), shape = RectangleShape
                )
                .padding(4.dp)
        )

    }
}

@Composable
fun renderPartidasEspeciales(listaPartidas: List<Partida>, usuario: Usuario) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 4.dp, end = 72.dp, top = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var cont = contadorPartidasUsuario(listaPartidas, usuario)
        var contPartidas = contadorPartidasOficiales(listaPartidas)
        Image(
            imageVector = Icons.Default.Games,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "Cantidad de partidas fuera o Milsims: $cont de ${contPartidas}",
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp, color = colorResource(id = R.color.VerdeMilitar), shape = RectangleShape
                )
                .padding(4.dp)
        )

    }
}

@Composable
fun renderEntrenamientos(listaPartidas: List<Partida>, usuario: Usuario) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 34.dp, end = 72.dp, top = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var cont = contadorPartidasUsuario(listaPartidas, usuario)
        var contPartidas = contadorPartidasOficiales(listaPartidas)
        Image(
            imageVector = Icons.Default.Games,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = "Cantidad de Entrenamientos:     $cont de ${contPartidas}",
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    2.dp, color = colorResource(id = R.color.VerdeMilitar), shape = RectangleShape
                )
                .padding(4.dp)
        )

    }
}

fun contadorPartidasOficiales(listaPartidas: List<Partida>): Int {
    var partidas = mutableListOf<String>()
    if (listaPartidas.isNullOrEmpty()) {
        return 0
    } else {
        listaPartidas.forEach {
            if (it.isOficial && !partidas.contains(it.id)) {
                partidas.add(it.id)
            }
        }
        return partidas.size
    }


}

fun contadorPartidasUsuario(listaPartidas: List<Partida>, usuario: Usuario): Int {

    var partidasUsuario = mutableListOf<String>()

    if (!listaPartidas.isEmpty()) {
        listaPartidas.forEach { partida ->
            if (partida.isOficial) {
                usuario.partidas?.forEach { idpartida ->
                    if (partida.id == idpartida && !partidasUsuario.contains(idpartida)) {
                        partidasUsuario.add(idpartida)
                    }
                }
            }
        }
        return partidasUsuario.size
    }
    return 0
}

@Composable
fun renderButtonNavigations(
    navController: NavController,
    usuario: Usuario,
    nickname: String,
    password: String,
    viewModelMain: MainViewModel
) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        renderButtonBack {
            navController.navigate(AppScreen.MainScreen.route)
        }
    }
}


@Composable
fun renderButtonBack(onBackPressed: () -> Unit) {
    Button(onClick = onBackPressed) {
        Text(text = "Volver")
    }
}

@Composable
private fun renderNickName(
    nickname: String, viewModel: PerfilScreenViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 28.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = nickname,
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(end = 42.dp)
                .border(
                    2.dp, color = colorResource(id = R.color.VerdeMilitar), shape = RectangleShape
                )
                .padding(4.dp)
        )
    }
}

@Composable
private fun renderPassword(
    password: String, viewModel: PerfilScreenViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Lock,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = password, modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .border(
                    2.dp, color = colorResource(id = R.color.VerdeMilitar), shape = RectangleShape
                )
                .padding(4.dp)
        )
        IconButton(onClick = { viewModel.onClickEdit(true) }) {
            Image(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar Nickname",
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}

