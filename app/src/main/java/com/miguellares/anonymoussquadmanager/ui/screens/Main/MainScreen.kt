package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.DrawerMenuApp
import com.miguellares.anonymoussquadmanager.components.HomeBannerView
import com.miguellares.anonymoussquadmanager.components.TopBar
import com.miguellares.anonymoussquadmanager.models.Notificaciones
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.navigation.ButtonMenu
import com.miguellares.anonymoussquadmanager.ui.screens.AddEvent.AddEventDialog
import com.miguellares.anonymoussquadmanager.ui.screens.ExitDialog.ExitDialog
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.NotificacionesDialog.NotificacionesDialog
import com.miguellares.anonymoussquadmanager.utils.Utils

@Composable
fun MainScreen(
    navController: NavController,
    scrollState: ScrollState,
    usuario: Usuario,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val mainNavController = navController
    val mainScrollState = scrollState

    Scaffold(modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBar(usuario, viewModel, mainNavController, scaffoldState, scope) },
        drawerContent = { DrawerMenuApp(mainScrollState, navController, usuario) },
        bottomBar = {
            ButtonMenu(navController = mainNavController) {
               /* mainNavController.navigate(it.route) {
                    mainNavController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }*/
                viewModel.onClickAddEvent(true)
            }
        }) {
        Column {
            renderBodyEvents(scrollState, viewModel, usuario, navController)
            mySpace(espacio = 10)
        }

    }
}

@Composable
fun renderBanner() {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(), contentAlignment = Alignment.TopCenter
    ) {
        HomeBannerView(5)
    }
}

@Composable
fun renderBodyEvents(
    scrollState: ScrollState,
    viewModel: MainViewModel,
    usuario: Usuario,
    navController: NavController
) {
    val isEvent by viewModel.isEvent.observeAsState(initial = false)
    val isEntrenamiento by viewModel.isEntrenamiento.observeAsState(initial = false)
    val isEspecialGame by viewModel.isEspecialGame.observeAsState(initial = false)
    val lista by viewModel.partidas.observeAsState(initial = emptyList())
    val especial by viewModel.especial.observeAsState(initial = emptyList())
    val entrenamiento by viewModel.entrenamientos.observeAsState(initial = emptyList())
    val notificaciones by viewModel.notificaciones.observeAsState(
        initial = Notificaciones(
            "",
            "",
            vistoPor = emptyList()
        )
    )
    val onClickExit by viewModel.onClickExit.observeAsState(initial = false)
    val existNotificacion by viewModel.ExistNotification.observeAsState(initial = false)
    val onClickAddEvents by viewModel.openDialogPerfil.observeAsState(initial = false)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        viewModel.getAllGames("Partida") {}
        viewModel.getAllGames("Entrenamiento") {}
        viewModel.getAllGames("Partida Especial") {}
        usuario.email?.let { viewModel.checkNotifications(it) }
        buildBody(
            entrenamiento = entrenamiento,
            especial = especial,
            partida = lista,
            navController = navController,
            scrollState = scrollState,
            usuario = usuario,
            viewModel = viewModel,
            isEntrenamiento,
            isEvent,
            isEspecialGame,
            existNotificacion,
            notificaciones,
            onClickExit,
            onClickAddEvents
        )

    }

}

@Composable
fun buildBody(
    entrenamiento: List<Partida>,
    especial: List<Partida>,
    partida: List<Partida>,
    navController: NavController,
    scrollState: ScrollState,
    usuario: Usuario,
    viewModel: MainViewModel,
    isEntrenamiento: Boolean,
    isEvent: Boolean,
    isEspecialGame: Boolean,
    existNotificacion: Boolean,
    notificaciones: Notificaciones,
    onClickExit: Boolean,
    onClickAddEvents: Boolean
) {
    mySpace(espacio = 10)
    bindTitleEvents("Proximas partidas", 16)
    if (!isEvent) {
        renderWithOutEvents("partidas")
    } else {
        renderEvents(partida, scrollState, usuario, viewModel, navController)
        mySpace(espacio = 10)
    }
    if (existNotificacion) {
        if (!notificaciones.vistoPor?.contains(usuario.email)!!) {
            showNotifications(usuario, viewModel, notificaciones)
        }
    }
    if(onClickAddEvents){
        AddEventDialog(navController = navController, usuario = usuario)
    }
    if (onClickExit) {
        ExitDialog(viewModel = viewModel, navController = navController)
    }
    mySpace(espacio = 10)
    bindTitleEvents("Eventos Especiales", 16)
    if (!isEspecialGame) {
        renderWithOutEvents("partidas especiales")
    } else {
        renderEventsSpecial(
            lista = especial,
            scrollState = scrollState,
            usuario = usuario,
            viewModel = viewModel,
            navController = navController
        )
    }
    mySpace(espacio = 10)
    bindTitleEvents("Entrenamientos", 16)
    if (!isEntrenamiento) {
        renderWithOutEvents("partidas de entrenamiento")
    } else {
        renderEventsEntrenamiento(
            lista = entrenamiento,
            scrollState = scrollState,
            usuario = usuario,
            viewModel = viewModel,
            navController = navController
        )
    }
    renderBanner()
    mySpace(espacio = 50)
}

@Composable
fun showNotifications(usuario: Usuario, viewModel: MainViewModel, notificaciones: Notificaciones) {
    Dialog(
        onDismissRequest = { viewModel.openDialogPerfil(false) },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        NotificacionesDialog(viewModel, usuario, notificaciones)
    }

}


@Composable
fun renderEvents(
    lista: List<Partida>,
    scrollState: ScrollState,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scrollable(scrollState, orientation = Orientation.Horizontal)

        ) {
            items(lista.size) { index ->
                EventosItem(
                    partida = lista[index],
                    usuario = usuario,
                    viewModel = viewModel,
                    navController = navController
                ) {
                    navController.navigate("${AppScreen.Details.route}/$index")
                }
            }
        }
    }

}


@Composable
fun renderEventsSpecial(
    lista: List<Partida>,
    scrollState: ScrollState,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scrollable(scrollState, orientation = Orientation.Horizontal)

        ) {
            items(lista.size) { index ->
                EventosItemEspecialGame(
                    partida = lista[index],
                    usuario = usuario,
                    viewModel = viewModel,
                    navController = navController
                ) {
                    navController.navigate("${AppScreen.SpecialGame.route}/$index")
                }
            }
        }
    }

}

@Composable
fun renderEventsEntrenamiento(
    lista: List<Partida>,
    scrollState: ScrollState,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scrollable(scrollState, orientation = Orientation.Horizontal)

        ) {
            items(lista.size) { index ->
                EventosItemTraining(
                    partida = lista[index],
                    usuario = usuario,
                    viewModel = viewModel,
                    navController = navController
                ) {
                    navController.navigate("${AppScreen.Training.route}/${index}")
                }
            }
        }
    }

}


@Composable
fun EventosItem(
    partida: Partida,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController,
    onClickItem: () -> Unit = {}
) {
    val nameInvited by viewModel.nameInvited.observeAsState(initial = "")
    val dateMovil by viewModel.dateMovil.observeAsState(initial = "")
    var isToday: Boolean = false

    viewModel.getDateMovil()
    if (dateMovil == partida.fecha) {
        isToday = true
    }

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(horizontal = 5.dp)
            .clickable { onClickItem() },
        shape = RoundedCornerShape(19.dp),
        backgroundColor = if (partida.isOficial) colorResource(id = R.color.VerdeMilitar)
        else colorResource(id = R.color.white),
        elevation = 2.dp,
        border = BorderStroke(1.dp, colorResource(id = R.color.YellowAnonymous))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            renderDate(partida.fecha, isToday)
            renderGamers(partida.jugadores, 30, viewModel)
            renderField(partida.campo, navController = navController, viewModel)
            renderButtonInviteAddorRemove(usuario, partida, viewModel, nameInvited)


        }
    }

}

@Composable
fun EventosItemEspecialGame(
    partida: Partida,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController,
    onClickItem: () -> Unit = {}
) {
    val nameInvited by viewModel.nameInvited.observeAsState(initial = "")
    val dateMovil by viewModel.dateMovil.observeAsState(initial = "")
    var isToday: Boolean = false

    viewModel.getDateMovil()
    if (dateMovil == partida.fecha) {
        isToday = true
    }

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(horizontal = 5.dp)
            .clickable { onClickItem() },
        shape = RoundedCornerShape(19.dp),
        backgroundColor = if (partida.isOficial) colorResource(id = R.color.VerdeMilitar)
        else colorResource(id = R.color.white),
        elevation = 2.dp,
        border = BorderStroke(1.dp, colorResource(id = R.color.YellowAnonymous))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            renderDate(partida.fecha, isToday)
            renderGamers(partida.jugadores, 30, viewModel)
            renderField(partida.campo, navController = navController, viewModel)
            renderButtonInviteAddorRemoveEspecial(usuario, partida, viewModel)


        }
    }

}

@Composable
fun EventosItemTraining(
    partida: Partida,
    usuario: Usuario,
    viewModel: MainViewModel,
    navController: NavController,
    onClickItem: () -> Unit = {}
) {
    val nameInvited by viewModel.nameInvited.observeAsState(initial = "")
    val dateMovil by viewModel.dateMovil.observeAsState(initial = "")
    var isToday = false

    viewModel.getDateMovil()
    if (dateMovil == partida.fecha) {
        isToday = true
    }

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(horizontal = 5.dp)
            .clickable { onClickItem() },
        shape = RoundedCornerShape(19.dp),
        backgroundColor = if (partida.isOficial) colorResource(id = R.color.VerdeMilitar)
        else colorResource(id = R.color.white),
        elevation = 2.dp,
        border = BorderStroke(1.dp, colorResource(id = R.color.YellowAnonymous))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            renderDate(partida.fecha, isToday)
            renderGamers(partida.jugadores, 30, viewModel)
            renderField(partida.campo, navController = navController, viewModel)
            renderButtonInviteAddorRemoveTraining(usuario, partida, viewModel, nameInvited)


        }
    }

}


@Composable
fun renderButtonInviteAddorRemove(
    usuario: Usuario, partida: Partida, viewModel: MainViewModel, nameInvited: String
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth()
    ) {

        if (!partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = {
                    viewModel.addGamerToGame(Utils.PARTIDA, usuario, partida)
                    viewModel.onClickInscribeMe(partida, usuario)
                },
                Modifier
                    .width(30.dp)
                    .height(30.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
        if (partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = { viewModel.removeGamerFromGame(Utils.PARTIDA, usuario, partida) },
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "eliminar inscripción"
                )
            }
        }
    }

}

@Composable
fun renderButtonInviteAddorRemoveEspecial(
    usuario: Usuario, partida: Partida, viewModel: MainViewModel
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth()
    ) {

        if (!partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = {
                    viewModel.addGamerToGame(Utils.PARTIDA_ESPECIAL, usuario, partida)
                    viewModel.onClickInscribeMe(partida, usuario)
                },
                Modifier
                    .width(30.dp)
                    .height(30.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
        if (partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = {
                    viewModel.removeGamerFromGame(
                        Utils.PARTIDA_ESPECIAL,
                        usuario,
                        partida
                    )
                },
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "eliminar inscripción"
                )
            }
        }
    }

}

@Composable
fun renderButtonInviteAddorRemoveTraining(
    usuario: Usuario, partida: Partida, viewModel: MainViewModel, nameInvited: String
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth()
    ) {

        if (!partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = {
                    viewModel.addGamerToGame(Utils.ENTRENAMIENTO, usuario, partida)
                    viewModel.onClickInscribeMe(partida, usuario)
                },
                Modifier
                    .width(30.dp)
                    .height(30.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
        if (partida.jugadores.contains(usuario.nickName)) {
            IconButton(
                onClick = { viewModel.removeGamerFromGame(Utils.ENTRENAMIENTO, usuario, partida) },
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "eliminar inscripción"
                )
            }
        }
    }

}

@Composable
private fun renderGamers(
    jugadores: List<String>, totalUsuarios: Int, viewModel: MainViewModel
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(5.dp)

        ) {
            Text(text = "Jugadores:")
        }
        Box(
            modifier = Modifier.padding(5.dp)

        ) {
            Row() {
                var cont = contadorActive(jugadores)
                Text(text = "${cont} de $totalUsuarios", maxLines = 1)
            }

        }

    }
}

fun contadorActive(jugadores: List<String>): Int {
    var result = 0
    jugadores.forEach {
        if (!it.contains("(inv)-")) {
            result += 1
        }
    }
    return result
}

@Composable
private fun renderDate(fecha: String, isToday: Boolean) {

    Row(
        modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = "Partida:")
        }
        Box(
            modifier = Modifier
                .padding(5.dp)
                .border(
                    1.dp,
                    if (isToday) colorResource(id = R.color.RedAnonymous)
                    else colorResource(id = R.color.YellowAnonymous)
                )

        ) {
            Text(text = fecha, modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}

@Composable
private fun renderField(campo: String, navController: NavController, viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = "Campo:")
        }
        Box(
            modifier = Modifier.padding(5.dp)

        ) {
            Text(text = campo)
        }
    }
}


@Composable
private fun renderWithOutEvents(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp)
        ) {
            Card(
                shape = RoundedCornerShape(90.dp), border = BorderStroke(
                    2.dp, color = colorResource(
                        id = R.color.black
                    )
                ), elevation = 2.dp, modifier = Modifier.padding(top = 3.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.no_event_solider),
                    contentDescription = "",
                    Modifier
                        .width(50.dp)
                        .height(50.dp),
                    Alignment.Center

                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Card(
                shape = RoundedCornerShape(16.dp), border = BorderStroke(
                    2.dp, color = colorResource(
                        id = R.color.black
                    )
                ), elevation = 2.dp
            ) {
                Text(
                    text = "Todavia no hay $title propuestas, podrias ser el primero, usa el icono + en la barra inferior.",
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

    }
}

@Composable
fun mySpace(espacio: Int) {
    Spacer(modifier = Modifier.padding(top = espacio.dp))
}

@Composable
fun bindTitleEvents(title: String, size: Int) {
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$title",
            textAlign = TextAlign.Center,
            fontSize = size.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
