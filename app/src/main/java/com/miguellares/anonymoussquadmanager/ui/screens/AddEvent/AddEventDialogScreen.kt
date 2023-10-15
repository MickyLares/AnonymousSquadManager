package com.miguellares.anonymoussquadmanager.ui.screens.AddEvent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.HomeBannerView
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel
import com.miguellares.anonymoussquadmanager.ui.screens.bindTitleEvents
import com.miguellares.anonymoussquadmanager.ui.screens.mySpace
import java.util.*


@Composable
fun AddEventDialog(
    navController: NavController,
    usuario: Usuario,
    viewModel: AddEventViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    mainViewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {


    Dialog(
        onDismissRequest = {

            viewModel.onDissmisDialog(false)
            mainViewModel.onClickAddEvent(false)
        },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        AddEvenScreen(navController, usuario, viewModel)
    }
}

@Composable
fun AddEvenScreen(navController: NavController, usuario: Usuario, viewModel: AddEventViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val selectDateTex by viewModel.selectedDateText.observeAsState(initial = "")
    val typeEvent by viewModel.typeEvent.observeAsState(initial = "")
    val field by viewModel.selectField.observeAsState(initial = "")
    val listField by viewModel.listField.observeAsState(initial = mutableListOf())
    val loading by viewModel.loading.observeAsState(initial = false)
    viewModel.getListOfField()

    Card(
        modifier = Modifier
            .padding(1.dp)
            .background(color = Color.Transparent)
            .fillMaxWidth()
            .height(590.dp)

    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.YellowAnonymous),
                            colorResource(id = R.color.white)
                        )
                    )
                )
                .padding(horizontal = 26.dp, vertical = 6.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            bindTitleEvents(title = "Partida propuesta por:", size = 16)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, colorResource(id = R.color.black_darkness))
                    .fillMaxWidth()
            ) {
                Text(text = usuario.nickName ?: "")
            }
            bindTitleEvents(title = "Elige Campo", size = 16)
            renderListField(listField, viewModel)
            mySpace(espacio = 5)
            bindTitleEvents(title = "Elige fecha", size = 16)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { viewModel.showDatePickerDialog(context, calendar) }) {
                    Image(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Calendario"
                    )
                }
                Text(text = selectDateTex, modifier = Modifier.padding(4.dp))
            }
            mySpace(espacio = 5)
            bindTitleEvents(title = "Elige tipo de partida:", size = 16)

            if (usuario.isAdmin) {
                mySpace(espacio = 5)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = typeEvent == "Entrenamiento",
                        onClick = { viewModel.onClickRadioButton("Entrenamiento") })
                    Text(text = "Entrenamiento", modifier = Modifier.padding(4.dp))
                }
            }
            mySpace(espacio = 5)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = typeEvent == "Partida",
                    onClick = { viewModel.onClickRadioButton("Partida") })
                Text(text = "Partida", modifier = Modifier.padding(4.dp))
            }
            mySpace(espacio = 5)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = typeEvent == "Partida Especial",
                    onClick = { viewModel.onClickRadioButton("Partida Especial") })
                Text(text = "Partida Especial", Modifier.padding(4.dp))
            }
            mySpace(espacio = 20)
            Row(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        navController.navigate(AppScreen.MainScreen.route)
                        viewModel.onDissmisDialog(false)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(50.dp)
                        .height(50.dp)
                        .border(
                            BorderStroke(1.dp, color = colorResource(id = R.color.white)),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        modifier = Modifier.clip(
                            CircleShape
                        )
                    )
                }
                mySpace(espacio = 10)
                if (loading) {
                    renderProgressBar()
                }
                IconButton(
                    onClick = {

                        viewModel.addNewEvent(
                            usuario,
                            typeEvent,
                            field,
                            selectDateTex
                        )
                        navController.navigate(AppScreen.MainScreen.route)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(50.dp)
                        .height(50.dp)
                        .border(
                            BorderStroke(1.dp, color = colorResource(id = R.color.black)),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.clip(
                            CircleShape
                        )
                    )
                }


            }

            mySpace(espacio = 5)
            HomeBannerView(padding = 1)
        }
    }
}

@Composable
fun renderProgressBar() {
    Row(
        modifier = Modifier.padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.RedAnonymous))
    }

}

@Composable
fun renderListField(listField: List<String>, viewModel: AddEventViewModel) {

    listField.forEach {
        fieldItem(title = it, viewModel = viewModel)
    }
}

@Composable
fun fieldItem(title: String, viewModel: AddEventViewModel) {
    val selectedField by viewModel.selectField.observeAsState(initial = "")

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
            .border(1.dp, colorResource(id = R.color.black_darkness)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, modifier = Modifier
            .padding(top = 5.dp)
            .clickable {
                viewModel.selectField(title)

            })
        if (selectedField == title) {
            Image(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }

    }


}
