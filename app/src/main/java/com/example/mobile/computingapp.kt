package com.example.mobile

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun MobileComputingApp(
    appState: appstate = rememberMobileComputingAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "home"
    ) {
        composable(route = "login") {
            val context = LocalContext.current
            context.startActivity(Intent(context,MainActivity::class.java))
        }
        composable(route = "home") {
            Home(navController = appState.navController)
        }
        composable(route = "Addition") {
            val context = LocalContext.current
            context.startActivity(Intent(context,MainActivity4::class.java))

        }
    }
}