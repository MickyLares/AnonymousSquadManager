package com.miguellares.anonymoussquadmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.miguellares.anonymoussquadmanager.navigation.AppNavigation
import com.miguellares.anonymoussquadmanager.ui.screens.Login.LoginViewModel
import com.miguellares.anonymoussquadmanager.ui.theme.AnonymousSquadManagerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            AnonymousSquadManagerTheme (darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MobileAds.initialize(this)
                    FirebaseApp.initializeApp(this)
                    val context = LocalContext.current.applicationContext

                    AppNavigation(context,viewModel)
                }
            }
        }
    }
}

