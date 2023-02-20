package com.example.mobile

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile.model.Remind

@Composable
fun MobileComputingApp2(
    AppState: appstate = rememberMobileComputingAppState()
) {
    NavHost(
        navController = AppState.navController,
        startDestination = "Addition"
    ) {
        composable(route = "login") {
            val context = LocalContext.current
            context.startActivity(Intent(context,MainActivity::class.java))
        }
        composable(route = "home") {
            Home()
        }
        composable(route = "Addition") {
            Remind(onBackPress = AppState::navigateBack)
        }
    }
}