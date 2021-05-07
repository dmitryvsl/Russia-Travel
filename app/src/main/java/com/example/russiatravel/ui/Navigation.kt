package com.example.russiatravel.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russiatravel.ui.filter.FilterScreen
import com.example.russiatravel.ui.login.CreateAccountScreen
import com.example.russiatravel.ui.login.LoginScreen
import com.example.russiatravel.ui.login.StartScreen
import com.example.russiatravel.ui.login.WelcomeScreen

sealed class Route(val id: String) {
    object Start : Route("start")
    object Filter : Route("filter")
}

@ExperimentalAnimationApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {

        Route::class.sealedSubclasses.forEach { route ->
            composable(route.objectInstance!!.id) { Contents(it, navController) }
        }

    }

}

@ExperimentalAnimationApi
@Composable
fun Contents(route: NavBackStackEntry, navController: NavController) {
    Crossfade(targetState = route) {
        when (it.arguments?.getString(KEY_ROUTE)) {
            Route.Start.id -> StartScreen(navController)
            Route.Filter.id -> FilterScreen(navController)
            else -> Text("UNKNOWN PAGE")
        }
    }
}