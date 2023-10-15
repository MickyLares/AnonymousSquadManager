package com.miguellares.anonymoussquadmanager.ui.screens.NormasAnonymousScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.components.topBarInterior
import com.miguellares.anonymoussquadmanager.models.Articulos
import com.miguellares.anonymoussquadmanager.models.BodyConstitucion
import com.miguellares.anonymoussquadmanager.models.Constitucion
import com.miguellares.anonymoussquadmanager.models.Usuario


@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun NormasAnonymousScreen(
    navController: NavController,
    scrollState: ScrollState,
    usuario: Usuario
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { topBarInterior(navController = navController,"") },
    ) {
        renderBodyNormas(scrollState)
    }


}

@Composable
private fun renderBodyNormas(scrollState: ScrollState) {
    val constitucion = Constitucion.AnonymousConstitution
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(scrollState, Orientation.Vertical, true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(constitucion.constitucion) { item: BodyConstitucion ->
            ReadChapel(bodyConstitucion = item)
        }
    }
}

@Composable
fun ReadChapel(bodyConstitucion: BodyConstitucion) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = bodyConstitucion.titulo,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            textAlign = TextAlign.Center
        )
        bodyConstitucion.capitulos.forEach {
            ReadArticles(articulos = it)
        }
    }

}

@Composable
fun ReadArticles(articulos: Articulos) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${articulos.numero} ${articulos.contenido}",
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )

    }

}