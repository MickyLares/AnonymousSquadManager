package com.miguellares.anonymoussquadmanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miguellares.anonymoussquadmanager.R
import com.miguellares.anonymoussquadmanager.components.topBarInterior

@Composable
fun WorkingForYou(navController: NavController) {
    Scaffold(topBar = { topBarInterior(navController = navController, sectionName = "") }) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(9.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_event_solider),
                        contentDescription = ""
                    )
                }
                bindTitleEvents(title = "Estamos desarrollando esta página", size = 16)
                bindTitleEvents(
                    title = "Si tienes alguna sugerencia escribe a mlaresc@gmail.com",
                    size = 12
                )

            }
        }
    }
}