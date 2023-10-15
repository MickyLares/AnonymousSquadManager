package com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInteriorExitFile
import com.miguellares.anonymoussquadmanager.models.FileExit
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace

@Composable
fun FileExitScreen(
    usuario: Usuario,
    navController: NavController,
    viewModel: FileExitViewModel = viewModel()
) {
    val files by viewModel.FileExit.observeAsState(initial = emptyList())
    val openDialog by viewModel.openDialog.observeAsState(initial = false)
    val myFile by viewModel.myFile.observeAsState(initial = FileExit())


    viewModel.getFileExit()
    Scaffold(
        topBar = {
            topBarInteriorExitFile(
                navController = navController,
                sectionName = "Expedientes"
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(6.dp))
                .padding(4.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
              items(files.size){ index ->
                  renderFiles(usuario = usuario, fileExit = files[index] , viewModel = viewModel ){
                      navController.navigate("${AppScreen.FileExitScreen.route}/$index")
                  }
                  mySpace(espacio = 5)
              }

            }
        }
    }
}

@Composable
fun openDialogVote(usuario: Usuario, fileExit: FileExit, viewModel: FileExitViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = { viewModel.openDialog(false, fileExit) },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            VoteDialog(fileExit = fileExit, usuario = usuario, viewModel = viewModel)
        }
    }
}

@Composable
fun VoteDialog(fileExit: FileExit, usuario: Usuario, viewModel: FileExitViewModel) {
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
            bindTitleEvents(title = "Votación para Expulsión", size = 15)
            bindTitleEvents(
                title = "¿Esta Usted de acuerdo con expulsar del Escuadrón Anonymous a ${fileExit.nickName}?",
                size = 10
            )
            renderButtonVote(fileExit, usuario, viewModel)
        }
    }

}

@Composable
fun renderButtonVote(fileExit: FileExit, usuario: Usuario, viewModel: FileExitViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Button(onClick = { viewModel.voteSi(fileExit, usuario) }) {
            Text(text = "Si")
        }
        Button(onClick = { viewModel.voteNo(fileExit, usuario) }) {
            Text(text = "No")
        }
    }
}

@Composable
fun renderFiles(
    usuario: Usuario,
    fileExit: FileExit,
    viewModel: FileExitViewModel,
    onClickItem: ()->Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClickItem() }
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.white),
                        colorResource(id = R.color.teal_200),
                        colorResource(id = R.color.RedAnonymous)
                    )
                )
            )
            .border(
                1.dp,
                color = colorResource(id = R.color.VerdeMilitar),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "id: ${fileExit.idFile}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Expedientado: ${fileExit.nickName}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "fecha: ${fileExit.fecha}")
            }


        }
    }
}
