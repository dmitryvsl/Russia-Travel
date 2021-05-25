package com.example.russiatravel.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.drawer.About
import com.example.russiatravel.presentation.ui.drawer.Profile
import com.example.russiatravel.presentation.ui.drawer.ScaffoldDrawer
import com.example.russiatravel.presentation.ui.drawer.Settings
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.sight.SightDetail
import com.example.russiatravel.presentation.ui.sight.SightList
import com.example.russiatravel.presentation.ui.filter.FilterScreen
import com.example.russiatravel.presentation.ui.filter.TopBar
import com.example.russiatravel.ui.login.StartScreen
import kotlinx.coroutines.launch

sealed class Route(val id: String) {
    object StartScreen : Route ("start_screen")
    object Filter : Route("filter")
    object SightList : Route("sight_list")
    object SightDetail : Route("sight_detail")
    object Profile : Route ("profile")
    object Settings : Route ("settings")
    object About : Route ("about")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination = if (SharedPreferences.checkTokenExist()) Route.Filter.id else Route.StartScreen.id
    NavHost(navController = navController, startDestination = Route.SightDetail.id) {
        composable(Route.StartScreen.id){ navBackStackEntry ->
            Contents( navBackStackEntry, navController)
        }
        composable(Route.StartScreen.id){ navBackStackEntry ->
            Contents(navBackStackEntry,navController)
        }
        composable(Route.Filter.id) { navBackStackEntry ->
            ScaffoldDrawer ("Расположение", navController) {
                Contents(navBackStackEntry, navController)
            }
        }

        composable(
            route = Route.SightList.id + "/{localityId}",
            arguments = listOf(navArgument("localityId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            ScaffoldDrawer("Достопримечательности", navController) {
                Contents(
                    route = navBackStackEntry,
                    navController = navController,
                    localityId = navBackStackEntry.arguments?.getInt("localityId") ?: 0
                )
            }
        }

        composable(
            route = Route.SightDetail.id /*+ "/{sightId}"*/,
            arguments = listOf(navArgument("sightId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            Contents(
                route = navBackStackEntry,
                navController = navController,
                sightId = navBackStackEntry.arguments?.getInt("sightId")
            )
        }

        composable(Route.Profile.id){navBackStackEntry ->
            ScaffoldDrawer(topBarTitle = "Профиль", navController = navController, icon = Icons.Default.ArrowBack) {
                Contents(route = navBackStackEntry , navController = navController)
            }
        }
        composable(Route.Settings.id){navBackStackEntry ->
            ScaffoldDrawer(topBarTitle = "Настройки", navController = navController, icon = Icons.Default.ArrowBack) {
                Contents(route = navBackStackEntry , navController = navController)
            }
        }
        composable(Route.About.id){navBackStackEntry ->
            ScaffoldDrawer(topBarTitle = "О программе", navController = navController, icon = Icons.Default.ArrowBack) {
                Contents(route = navBackStackEntry , navController = navController)
            }
        }
    }
}


@Composable
fun Contents(
    route: NavBackStackEntry,
    navController: NavController,
    localityId: Int = 0,
    sightId: Int? = null
) {
    Crossfade(targetState = route) {
        when (it.arguments?.getString(KEY_ROUTE)) {
            Route.StartScreen.id -> StartScreen(navController)
            Route.Filter.id -> FilterScreen(navController)

            Route.SightList.id + "/{localityId}" -> SightList(
                navController,
                localityId = localityId
            )

            Route.SightDetail.id /* + "/{sightId}"*/ -> SightDetail(
                navController = navController,
                sightId!!
            )
            Route.Profile.id -> Profile()
            Route.Settings.id -> Settings()
            Route.About.id -> About()
            else -> Text("UNKNOWN PAGE")
        }
    }
}