package com.miguellares.anonymoussquadmanager.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miguellares.anonymoussquadmanager.LoginScreen
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.DrawerMenuItem
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.showError
import com.miguellares.anonymoussquadmanager.ui.screens.*
import com.miguellares.anonymoussquadmanager.ui.screens.AddEvent.AddEventDialog
import com.miguellares.anonymoussquadmanager.ui.screens.AddEvent.AddEventViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.Details.SpecialGame
import com.miguellares.anonymoussquadmanager.ui.screens.Details.TrainingScreen
import com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen.DetailsFileExitScreen
import com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen.FileExitScreen
import com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen.FileExitViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.Login.LoginViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.MapScreen.MyGoogleMaps
import com.miguellares.anonymoussquadmanager.ui.screens.NormasAnonymousScreen.NormasAnonymousScreen
import com.miguellares.anonymoussquadmanager.ui.screens.SquadManager.SquadManagerScreen


sealed class AppScreen(val route: String) {
    object LoginScreen : AppScreen("loginScreen")
    object MainScreen : AppScreen("MainScreen")
    object MapScreen : AppScreen("Maps")
    object Details : AppScreen("Detail")
    object Training : AppScreen("Training")
    object SpecialGame : AppScreen("EspecialGame")
    object FileExitScreen : AppScreen("FileExit")
    object DetailsFileExitScreen : AppScreen("DetailFileExit")

}


@SuppressLint("UnrememberedMutableState")
@Composable
fun AppNavigation(
    context: Context,
    viewModel: LoginViewModel,
    viewmodelDialogo: AddEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewmodelMainViewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModelFileExit: FileExitViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    val usuario by viewModel.usuario.observeAsState(initial = Usuario())
    val open by viewmodelDialogo.openDialog.observeAsState(initial = true)
    val nameField by viewmodelMainViewModel.nameField.observeAsState(initial = "")
    val partidas by viewmodelMainViewModel.partidas.observeAsState(initial = emptyList())
    val entrenamientos by viewmodelMainViewModel.entrenamientos.observeAsState(initial = emptyList())
    val partidasEspeciales by viewmodelMainViewModel.especial.observeAsState(initial = emptyList())
    val fileExitList by viewModelFileExit.FileExit.observeAsState(initial = emptyList())




    viewmodelMainViewModel.getAllGames("Partida") {
        showError(context, "Fallo al cargar las partidas")
    }
    viewmodelMainViewModel.getAllGames("Entrenamiento") {
        showError(context, "Fallo al cargar los entrenamientos")
    }
    viewmodelMainViewModel.getAllGames("Partida Especial") {
        showError(context, "Fallo al cargar las partidas Especiales")
    }

    viewModelFileExit.getFileExit()



    partidas.let {
        NavHost(navController = navController, startDestination = AppScreen.LoginScreen.route) {

            composable(AppScreen.LoginScreen.route) {
                LoginScreen(navController = navController, viewModel, context)
            }
            composable(AppScreen.MainScreen.route) {
                MainScreen(navController, scrollState, usuario)
            }
            fileExitList.let {
                composable(
                    "${AppScreen.FileExitScreen.route}/{index}",
                    listOf(navArgument("index") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val index = navBackStackEntry.arguments?.getInt("index")
                    index?.let {
                        val file = fileExitList[index]
                        DetailsFileExitScreen(
                            usuario,
                            file,
                            viewModel = viewModelFileExit,
                            navController = navController
                        )
                    }
                }
            }
            entrenamientos.let {
                composable(
                    "${AppScreen.Training.route}/{index}",
                    listOf(navArgument("index") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val index = navBackStackEntry.arguments?.getInt("index")
                    index?.let {
                        val partida = entrenamientos[index]
                        TrainingScreen(partida, usuario, navController)
                    }
                }
            }

            partidasEspeciales.let {
                composable(
                    "${AppScreen.SpecialGame.route}/{index}",
                    listOf(navArgument("index") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val index = navBackStackEntry.arguments?.getInt("index")
                    index?.let {
                        val partida = partidasEspeciales[index]
                        SpecialGame(partida = partida, navController as NavController, usuario)
                    }
                }
            }

            composable(AppScreen.FileExitScreen.route) {
                FileExitScreen(usuario, navController = navController)
            }

            composable(AppScreen.MapScreen.route) {
                MyGoogleMaps(name = nameField)
            }

            composable(ButtonMenuScreen.AddEvent.route) {
                AddEventDialog(navController, usuario)
            }

            composable(ButtonMenuScreen.PerfilScreen.route) {
                PerfilScreen(
                    usuario = usuario,
                    navController = navController,
                    listaPartidas = partidas,
                    partidasEspeciales = partidasEspeciales,
                    entrenamientos = entrenamientos
                )
            }
            DrawerMenuItem.rulesSquad.route?.let {
                composable(it) {
                    NormasAnonymousScreen(navController = navController, scrollState, usuario)
                }
            }
            DrawerMenuItem.visionSquad.route?.let {
                composable(it) {
                    WorkingForYou(navController = navController)
                }
            }
            DrawerMenuItem.misionSquad.route?.let {
                composable(it) {
                    WorkingForYou(navController = navController)
                }
            }
            DrawerMenuItem.CompraVenta.route?.let {
                composable(it) {
                    WorkingForYou(navController = navController)
                }
            }

            DrawerMenuItem.GestionEquipo.route?.let {
                composable(it) {
                    SquadManagerScreen(navController = navController, usuario.email ?: "")
                }
            }
            DrawerMenuItem.personalInfoConfig.route?.let {
                composable(it) {
                    PerfilScreen(
                        usuario = usuario,
                        navController = navController,
                        listaPartidas = partidas,
                        partidasEspeciales = partidasEspeciales,
                        entrenamientos = entrenamientos
                    )
                }
            }
            composable(
                "Detail/{index}",
                listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val partida = partidas[index]
                    if (partida.isOficial) {
                        DetailOficialScreen(partida = partida, navController, usuario)
                    } else {
                        DetailNoOficialScreen(
                            partida = partida,
                            navController = navController,
                            usuario
                        )
                    }
                }
            }
        }
    }
}


fun NavGraphBuilder.bottonNavigation(
    partidas: List<Partida>,
    partidasEspeciales: List<Partida>,
    entrenamientos: List<Partida>,
    usuario: Usuario,
    navController: NavController,
    scrollState: ScrollState
) {


    composable(ButtonMenuScreen.AddEvent.route) {
        AddEventDialog(navController, usuario)
    }
    composable(ButtonMenuScreen.PerfilScreen.route) {
        PerfilScreen(
            usuario = usuario,
            navController = navController,
            listaPartidas = partidas,
            partidasEspeciales = partidasEspeciales,
            entrenamientos = entrenamientos
        )
    }
    DrawerMenuItem.rulesSquad.route?.let {
        composable(it) {
            NormasAnonymousScreen(navController = navController, scrollState, usuario)
        }
    }
    DrawerMenuItem.visionSquad.route?.let {
        composable(it) {
            WorkingForYou(navController = navController)
        }
    }
    DrawerMenuItem.misionSquad.route?.let {
        composable(it) {
            MisionSquadScreen(navController = navController)
        }
    }
    DrawerMenuItem.CompraVenta.route?.let {
        composable(it) {
            WorkingForYou(navController = navController)
        }
    }
    DrawerMenuItem.GestionEquipo.route?.let {
        composable(it) {
            usuario.email?.let { email -> SquadManagerScreen(navController = navController, email) }
        }
    }
    DrawerMenuItem.personalInfoConfig.route?.let {
        composable(it) {
            PerfilScreen(
                usuario = usuario,
                navController = navController,
                listaPartidas = partidas,
                partidasEspeciales = partidasEspeciales,
                entrenamientos = entrenamientos
            )
        }
    }
}


sealed class ButtonMenuScreen(
    val route: String,
    val icon: ImageVector? = null,
    val title: String? = null
) {
    object AddEvent : ButtonMenuScreen("addEvent", Icons.Default.Add, "Proponer Partida")
    object EmptySpace : ButtonMenuScreen("empty")
    object PerfilScreen : ButtonMenuScreen("Perfil", Icons.Default.Person, "Perfil")

}

@Composable
fun ButtonMenu(navController: NavController, onClick: (ButtonMenuScreen) -> Unit) {
    val menuItem = listOf(
        ButtonMenuScreen.AddEvent
    )

    BottomNavigation(contentColor = colorResource(id = R.color.black_darkness)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuItem.forEach {
            BottomNavigationItem(
                label = { Text(text = it.title ?: "") },
                modifier = Modifier.background(color = colorResource(id = R.color.YellowAnonymous)),
                alwaysShowLabel = true,
                selectedContentColor = Color(R.color.white),
                unselectedContentColor = Color(R.color.RedAnonymous),
                selected = currentRoute == it.route,
                onClick = {
                    onClick(it)
                },
                icon = {
                    it.icon?.let { image ->
                        Icon(
                            imageVector = image,
                            contentDescription = ""
                        )
                    }
                }
            )
        }

    }
}
