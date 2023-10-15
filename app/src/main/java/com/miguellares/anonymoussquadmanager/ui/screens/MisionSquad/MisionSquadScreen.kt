package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInterior
import com.miguellares.anonymoussquadmanager.models.Mision
import com.miguellares.anonymoussquadmanager.ui.screens.MisionSquad.MisionScreenViewModel

@Composable
fun MisionSquadScreen(
    navController: NavController,
    viewModel: MisionScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val mision by viewModel.mision.observeAsState(initial = Mision())
    viewModel.getMisionSquad()
    renderBodyScreen(mision, navController = navController)


}

@Composable
fun renderBodyScreen(mision: Mision, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(6.dp))
    ) {
        Scaffold(topBar = {
            topBarInterior(
                navController = navController,
                sectionName = ""
            )
        }) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = colorResource(id = R.color.YellowAnonymous))
                ) {
                    renderOficialHeaderBody()
                }

                mySpace(espacio = 5)
                bindTitleEvents(title = mision.title ?: "", size = 16)
                mySpace(espacio = 5)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = colorResource(id = R.color.YellowAnonymous))
                ) {
                    mySpace(espacio = 3)
                    bindTitleEvents(title = mision.message ?: "", size = 14)
                    mySpace(espacio = 3)
                }

            }
        }
    }
}
