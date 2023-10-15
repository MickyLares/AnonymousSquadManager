package com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInteriorExitFile
import com.miguellares.anonymoussquadmanager.models.FileExit
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.showError
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents

@Composable
fun DetailsFileExitScreen(
    usuario: Usuario,
    fileExit: FileExit,
    viewModel: FileExitViewModel,
    navController: NavController
) {
    val voted by viewModel.voted.observeAsState(initial = true)
    viewModel.checkVote(fileExit, usuario)
    Scaffold(topBar = {
        topBarInteriorExitFile(
            navController = navController,
            sectionName = ""
        )
    }) {
        renderBodyFileExit(usuario, fileExit, voted, viewModel)
    }
}

@Composable
fun renderBodyFileExit(
    usuario: Usuario,
    fileExit: FileExit,
    voted: Boolean,
    viewModel: FileExitViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        bindTitleEvents(title = "Detalles:", size = 19)
        bindDetails(usuario = usuario, fileExit = fileExit, voted, viewModel)

    }

}

@Composable
fun bindDetails(
    usuario: Usuario,
    fileExit: FileExit,
    voted: Boolean,
    viewModel: FileExitViewModel
) {
    val context: Context = LocalContext.current
    Column(modifier = Modifier.border(1.dp, color = colorResource(id = R.color.VerdeMilitar))) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("fecha:${fileExit.fecha}", fontSize = 14.sp)

        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("id:${fileExit.idFile}", fontSize = 14.sp)

        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Nickname:${fileExit.nickName}", fontSize = 14.sp)

        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Motivo de expulsion:${fileExit.motivo}", fontSize = 14.sp)

        }
        bindTitleEvents(title = "Votos emitidos", size = 19)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Si:${fileExit.votosSI.size}", fontSize = 14.sp)

        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("No:${fileExit.votosNO.size}", fontSize = 14.sp)
        }

        if (!voted) {
            Column() {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "¿Esta Ud. de acuerdo con expulsar del Escuadrón a: ${fileExit.nickName}?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Button(
                        onClick = { viewModel.voteSi(fileExit, usuario = usuario) },
                        modifier = Modifier.padding(end = 4.dp),
                        enabled = !voted
                    ) {
                        Text(text = "Si")
                    }


                    Button(
                        onClick = { viewModel.voteNo(fileExit, usuario) },
                        modifier = Modifier.padding(start = 4.dp),
                        enabled = !voted
                    ) {
                        Text(text = "No")
                    }
                }
            }

        }
        if (fileExit.votosSI.size > fileExit.votosNO.size && fileExit.votosSI.size + fileExit.votosNO.size == 7) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        fileExit.email?.let {
                            viewModel.removeUserFromDB(it){
                                showError(context, "Usuario Eliminado")
                            }
                        }
                    },
                    modifier = Modifier.padding(end = 4.dp),
                ) {
                    Text(text = "Expulsar")
                }

            }
        }
    }

}
