package com.miguellares.anonymoussquadmanager.ui.screens.Details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInterior
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.campoField
import com.miguellares.anonymoussquadmanager.ui.screens.jugadores
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace
import com.miguellares.anonymoussquadmanager.utils.Utils

@Composable
fun TrainingScreen(partida: Partida, usuario: Usuario, navController: NavHostController){
    Card(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                topBarInterior(
                    navController = navController,
                    sectionName = "Partida Especial del ${partida.fecha}"
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = colorResource(id = R.color.VerdeMilitar))
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    renderBodyTraining(
                        usuario = usuario,
                        partida = partida
                    )
                }
                bindTitleEvents(title = "Información", size = 16)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = colorResource(id = R.color.VerdeMilitar))
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    Text(
                        text = "Los Instructores del Entrenamiento serán contactados por el Consejo de Administración del Escuadrón.",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                }
                mySpace(espacio = 15)
                bindTitleEvents(
                    title = "Nota: se deberá consultar con el Consejo de Administración la asistencia de invitados.",
                    size = 14
                )
                mySpace(espacio = 20)

            }
        }

    }
}
@Composable
fun renderBodyTraining(
    usuario: Usuario,
    partida: Partida,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            renderTrainingHeaderBody()
            mySpace(espacio = 5)
            bindTitleEvents(title = "Jugadores", size = 16)
            partida.jugadores.forEach {
                jugadores(Utils.ENTRENAMIENTO,name = it, partida)
            }
            mySpace(espacio = 5)
            bindTitleEvents(title = "Campo", size = 16)
            campoField(name = partida.campo)
            mySpace(espacio = 5)
            bindTitleEvents(title = "Fecha", size = 16)
            campoField(name = partida.fecha)
            mySpace(espacio = 15)

        }

    }
}
@Composable
fun renderTrainingHeaderBody() {
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
                painter = painterResource(id = R.drawable.legion_spain_jpge),
                contentDescription = "paisaje",
                contentScale = ContentScale.FillWidth

            )
        }

    }

}