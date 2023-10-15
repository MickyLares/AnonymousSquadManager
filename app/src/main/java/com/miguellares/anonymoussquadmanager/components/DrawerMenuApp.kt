package com.miguellares.anonymoussquadmanager.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.outlined.LocalPolice
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.renderBanner

@Composable
fun DrawerMenuApp(mainScrollState: ScrollState, navController: NavController, usuario: Usuario) {
    var menuList = emptyList<DrawerMenuItem>()
    if (!usuario.isAdmin) {
        menuList = listOf(
            DrawerMenuItem.personalInfoHeader,
            DrawerMenuItem.personalInfoConfig,
            DrawerMenuItem.divider,
            DrawerMenuItem.SquadInfoHeader,
           // DrawerMenuItem.misionSquad,
           // DrawerMenuItem.visionSquad,
            DrawerMenuItem.rulesSquad,
            DrawerMenuItem.divider,
         //   DrawerMenuItem.CompraVentaHeader,
        //    DrawerMenuItem.CompraVenta,
            DrawerMenuItem.publi
        )
    } else {

        menuList = listOf(
            DrawerMenuItem.personalInfoHeader,
            DrawerMenuItem.personalInfoConfig,
            DrawerMenuItem.divider,
            DrawerMenuItem.SquadInfoHeader,
         //   DrawerMenuItem.misionSquad,
         //   DrawerMenuItem.visionSquad,
            DrawerMenuItem.rulesSquad,
         //   DrawerMenuItem.divider,
        //    DrawerMenuItem.CompraVentaHeader,
        //    DrawerMenuItem.CompraVenta,
            DrawerMenuItem.divider,
            DrawerMenuItem.GestionEquipoHeader,
            DrawerMenuItem.GestionEquipo,
            DrawerMenuItem.divider,
            DrawerMenuItem.publi
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(mainScrollState)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.YellowAnonymous),
                        colorResource(id = R.color.white)
                    )
                )
            )
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Hola ${usuario.nickName}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                fontFamily = FontFamily.Cursive,
                fontSize = 18.sp
            )
        }
        menuList.forEach { item ->
            when {
                item.isDivider -> {
                    Divider(
                        modifier = Modifier
                            .padding(bottom = 19.dp, top = 19.dp)
                            .background(
                                colorResource(id = R.color.VerdeMilitar)
                            )
                    )
                }
                item.isHeader -> {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = item.title ?: "",
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(start = 19.dp, bottom = 19.dp, top = 19.dp)
                        )
                    }

                }
                item.isPubli -> {
                    Row(horizontalArrangement = Arrangement.Center) {
                        HomeBannerView(padding = 24)
                    }
                }
                else -> {
                    ItemMenu(item, navController)
                }
            }
        }
    }
}

@Composable
fun ItemMenu(item: DrawerMenuItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(6.dp)
    ) {
        Image(
            imageVector = item.icon!!,
            contentDescription = item.title,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = item.title!!, modifier = Modifier
                .weight(2.0f)
                .padding(bottom = 4.dp)
                .clickable {
                    item.route?.let {
                        navController.navigate(it) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
        )
    }
}

sealed class DrawerMenuItem(
    val icon: ImageVector? = null,
    val title: String? = null,
    val isDivider: Boolean = false,
    val isHeader: Boolean = false,
    val route: String? = null,
    val isPubli: Boolean = false

) {
    object personalInfoHeader : DrawerMenuItem(
        title = "Información Personal", isHeader = true
    )

    object personalInfoConfig : DrawerMenuItem(
        title = "Mi Perfil",
        icon = Icons.Default.Person,
        route = "MiPerfil"
    )

    object divider : DrawerMenuItem(isDivider = true)
    object SquadInfoHeader : DrawerMenuItem(
        title = "Reglas del Escuadrón",
        isHeader = true
    )

    object misionSquad : DrawerMenuItem(
        title = "Misión",
        icon = Icons.Default.Home,
        route = "mision"
    )

    object visionSquad : DrawerMenuItem(
        title = "Visión",
        icon = Icons.Default.Home,
        route = "Vision"
    )

    object rulesSquad : DrawerMenuItem(
        title = "Normas del Equipo",
        icon = Icons.Default.Rule,
        route = "Normas"

    )

    object CompraVentaHeader : DrawerMenuItem(
        title = "Compra - Venta",
        isHeader = true
    )

    object CompraVenta : DrawerMenuItem(
        title = "Compra - Venta",
        icon = Icons.Outlined.Sell,
        route = "Compra&Venta"
    )

    object GestionEquipoHeader : DrawerMenuItem(
        title = "Gestion del Equipo",
        isHeader = true
    )

    object GestionEquipo : DrawerMenuItem(
        title = "Gestion del Equipo",
        icon = Icons.Outlined.LocalPolice,
        route = "TeamManager"
    )

    object publi : DrawerMenuItem(title = "Publicidad", isPubli = true)
}