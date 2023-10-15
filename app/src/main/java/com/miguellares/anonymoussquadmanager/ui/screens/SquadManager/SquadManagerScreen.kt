package com.miguellares.anonymoussquadmanager.ui.screens.SquadManager

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Textsms
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInterior
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.DialogRegistro.RegistroScreenDialog
import com.miguellares.anonymoussquadmanager.ui.screens.ExitFileDialog.ExitFileDialogo
import com.miguellares.anonymoussquadmanager.ui.screens.NotificacionesDialog.NotificacionesWriteUI
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace


@Composable
fun SquadManagerScreen(
    navController: NavController,
    mailUser: String,
    viewModel: SquadManagerViewModel = viewModel()
) {
    val listaUsuarios by viewModel.listaUsuarios.observeAsState(initial = emptyList())
    val onClickRegistro by viewModel.isOpenDialog.observeAsState(initial = false)
    val onClickNotificationUI by viewModel.openNotificationUI.observeAsState(initial = false)
    val onClickExitFile by viewModel.onClickExitFile.observeAsState(initial = false)
    val myUser by viewModel.userToRemove.observeAsState(
        initial = Usuario(
            "", false, "", "", emptyList()
        )
    )
    val onClickOpenExitFile by viewModel.openExitFileDialog.observeAsState(initial = false)

    viewModel.getAllUser()
    Scaffold(modifier = Modifier.padding(horizontal = 4.dp, vertical = 5.dp),

        topBar = {
            topBarInterior(
                navController = navController, sectionName = "Gestion del Equipo"
            )
        }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = 2.dp,
            border = BorderStroke(1.dp, color = colorResource(id = R.color.VerdeMilitar))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .padding(5.dp, vertical = 4.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.white),
                                colorResource(id = R.color.YellowAnonymous)
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        bindTitleEvents(title = "Integrantes del Escuadrón Anonymous", size = 15)
                        renderListaMiembros(listaUsuarios, viewModel, mailUser)
                        bindTitleEvents(title = "Acciones adicionales", size = 15)
                        if (onClickRegistro) {
                            renderDialog(navController)
                        }
                        if (onClickNotificationUI) {
                            renderNotificacionesWriteUI(viewModel)
                        }
                        if (onClickOpenExitFile) {
                            navController.navigate(AppScreen.FileExitScreen.route)
                        }
                        if (onClickExitFile) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(290.dp)
                                    .clip(shape = RoundedCornerShape(6.dp))
                            ) {
                                renderExitFileUi(myUser, mailUser, viewModel)
                            }

                        }
                        renderRowButtons(viewModel, listaUsuarios.size)
                    }
                }
            }
        }
    }
}

@Composable
fun renderExitFileUi(usuario: Usuario, mailUser: String, viewModel: SquadManagerViewModel) {
    ExitFileDialogo(usuario, mailUser, viewModel)
}

@Composable
fun renderNotificacionesWriteUI(viewModel: SquadManagerViewModel) {
    NotificacionesWriteUI(viewModel = viewModel)
}

@Composable
fun renderDialog(navController: NavController) {
    RegistroScreenDialog(navController)
}

@Composable
fun renderRowButtons(
    viewModel: SquadManagerViewModel,
    cantidadUsuarios: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .horizontalScroll(
                rememberScrollState()
            )
            .clip(shape = RoundedCornerShape(7.dp))
            .background(color = colorResource(id = R.color.white))
            .border(1.dp, color = colorResource(id = R.color.VerdeMilitar)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { viewModel.onClickOpenNotificationUI(true) },
            shape = CircleShape,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            border = BorderStroke(1.dp, colorResource(id = R.color.VerdeMilitar))

        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Outlined.Textsms,
                    contentDescription = "Avisos al Escuadrón"
                )
                Text(
                    text = "Avisos",
                    color = colorResource(id = R.color.black_darkness),
                    fontSize = 9.sp
                )
            }

        }
        Button(
            onClick = {
                viewModel.onClickShowRegistro(true)
            },
            shape = CircleShape,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            enabled = cantidadUsuarios <= 31,
            border = BorderStroke(1.dp, colorResource(id = R.color.VerdeMilitar))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(imageVector = Icons.Outlined.PersonAdd, contentDescription = "Botón Añadir")
                Text(
                    text = "Añadir",
                    color = colorResource(id = R.color.black_darkness),
                    fontSize = 9.sp
                )
            }

        }
        Button(
            onClick = {
                viewModel.openDialogExitFile(true)
            },
            shape = CircleShape,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            border = BorderStroke(1.dp, colorResource(id = R.color.VerdeMilitar))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(imageVector = Icons.Outlined.AttachFile, contentDescription = "Files")
                Text(
                    text = "Expedientes",
                    color = colorResource(id = R.color.black_darkness),
                    fontSize = 9.sp
                )
            }

        }
    }
}

@Composable
fun renderListaMiembros(
    listado: List<Usuario>, viewModel: SquadManagerViewModel, mailUser: String
) {
    listado.forEach {
        renderUsuario(it, mailUser, viewModel)
    }
}

@Composable
fun renderUsuario(usuario: Usuario, mailUser: String, viewModel: SquadManagerViewModel) {
    val cantidadPartidas by viewModel.cantidadPartidas.observeAsState(initial = 0)

    val listado by viewModel.listadoPartida.observeAsState(initial = emptyList())
    val asistencia by viewModel.asistencia.observeAsState(initial = 0)
    val entrenamientos by viewModel.entrenamientos.observeAsState(initial = emptyList())
    val partidasEspeciales by viewModel.partidasEspeciales.observeAsState(initial = emptyList())
    viewModel.getAllGamesManager("Partida") {}
    viewModel.getAllGamesManager("Entrenamiento") {}
    viewModel.getAllGamesManager("Partida Especial") {}
    viewModel.contadorPartidasOficiales(listado, entrenamientos, partidasEspeciales, usuario)
    val myAsistencia = asistencia

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp,
            border = BorderStroke(1.dp, color = colorResource(id = R.color.VerdeMilitar))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.padding(horizontal = 3.dp)) {
                    Text(text = "Nickname:", modifier = Modifier.padding(end = 5.dp))
                    Text(text = usuario.nickName ?: "")
                }
                mySpace(espacio = 3)
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Administrador:", Modifier.padding(end = 5.dp, top = 5.dp))
                    var state = usuario.isAdmin
                    renderSwitch(state, viewModel, usuario)
                }
                mySpace(espacio = 3)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "nº Partidas oficiales del Escuadrón:",
                            modifier = Modifier.padding(horizontal = 2.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(text = "${cantidadPartidas}")
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Asistencia:",
                            modifier = Modifier.padding(horizontal = 2.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(text = "${myAsistencia}")
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {

                        IconButton(
                            onClick = {
                                viewModel.userToRemove(usuario)
                                viewModel.onClickExitFile(true)
                            }, modifier = Modifier
                                .border(
                                    1.dp,
                                    colorResource(id = R.color.VerdeMilitar),
                                    shape = CircleShape
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .width(56.dp)
                                .height(56.dp), enabled = asistencia < 4
                        ) {
                            Image(
                                imageVector = if (asistencia < 4) {
                                    Icons.Default.PersonOff
                                } else {
                                    Icons.Default.Person
                                }, contentDescription = "Exit File"
                            )
                        }

                    }
                }
                mySpace(espacio = 3)
            }
        }

    }

}

@Composable
fun renderSwitch(state: Boolean, viewModel: SquadManagerViewModel, usuario: Usuario) {
    var myState = state
    Switch(
        checked = state, onCheckedChange = {
            myState = !myState
            viewModel.updateAdmin(myState, usuario)
        }, colors = SwitchDefaults.colors(
            checkedThumbColor = colorResource(id = R.color.VerdeMilitar),
            uncheckedThumbColor = colorResource(id = R.color.RedAnonymous)
        )
    )
}
