package com.miguellares.anonymoussquadmanager.ui.screens.NotificacionesDialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.models.Notificaciones
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.ui.screens.Main.MainViewModel


@Composable
fun NotificacionesDialog(
    viewModel: MainViewModel,
    usuario: Usuario,
    notificaciones: Notificaciones
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.VerdeMilitar)),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Text(text = notificaciones.id ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp)
                    .border(
                        1.dp,
                        color = colorResource(R.color.VerdeMilitar)
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notificaciones.message ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                )
            }
            Row() {
                Button(onClick = {
                    viewModel.addVistoPor(usuario, notificaciones)
                    viewModel.openDialogPerfil(false)
                }) {
                    Text(text = "Aceptar")
                }
            }
        }
    }
}