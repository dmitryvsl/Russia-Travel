package com.example.russiatravel.presentation.ui

import android.util.Log
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
import com.example.russiatravel.presentation.ui.drawer.ScaffoldDrawer
import com.example.russiatravel.presentation.ui.drawer.Settings
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.sight.SightDetail
import com.example.russiatravel.presentation.ui.sight.SightList
import com.example.russiatravel.presentation.ui.filter.FilterScreen
import com.example.russiatravel.presentation.ui.filter.TopBar
import com.example.russiatravel.presentation.ui.sight.FeedbackScreen
import com.example.russiatravel.ui.login.StartScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class Route(val id: String) {
    object StartScreen : Route("start_screen")
    object Filter : Route("filter")
    object SightList : Route("sight_list")
    object SightDetail : Route("sight_detail")
    object Profile : Route("profile")
    object Settings : Route("settings")
    object About : Route("about")
    object Feedback : Route("feedback")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination =
        if (SharedPreferences.checkTokenExist()) Route.Filter.id else Route.StartScreen.id

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Route.StartScreen.id) { navBackStackEntry ->
            Contents(navBackStackEntry, navController)
        }
        composable(Route.StartScreen.id) { navBackStackEntry ->
            Contents(navBackStackEntry, navController)
        }
        composable(Route.Filter.id) { navBackStackEntry ->
            ScaffoldDrawer("Расположение", navController) {
                Contents(navBackStackEntry, navController)
            }
        }

        composable(
            route = Route.SightList.id + "/{localityId}",
            arguments = listOf(navArgument("localityId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val coroutineScope = rememberCoroutineScope()
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
            )
            val onActionClick: () -> Unit = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
            ScaffoldDrawer(
                topBarTitle = "Достопримечательности",
                navController = navController,
                showActions = true,
                onActionClicked = onActionClick
            ) {
                Contents(
                    route = navBackStackEntry,
                    navController = navController,
                    localityId = navBackStackEntry.arguments?.getInt("localityId"),
                    bottomSheetScaffoldState = bottomSheetScaffoldState
                )
            }
        }

        composable(
            route = Route.SightList.id + "/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.FloatType },
                navArgument("longitude") { type = NavType.FloatType }
            )
        ) { navBackStackEntry ->
            val coroutineScope = rememberCoroutineScope()
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
            )
            val onActionClick: () -> Unit = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
            ScaffoldDrawer(
                topBarTitle = "Достопримечательности",
                navController = navController,
                showActions = true,
                onActionClicked = onActionClick
            ) {
                Contents(
                    route = navBackStackEntry,
                    navController = navController,
                    latitude = navBackStackEntry.arguments?.getFloat("latitude"),
                    longitude = navBackStackEntry.arguments?.getFloat("longitude"),
                    bottomSheetScaffoldState = bottomSheetScaffoldState
                )
            }
        }

        composable(
            route = Route.SightDetail.id + "/{sightId}",
            arguments = listOf(navArgument("sightId") {
                type = NavType.IntType
            }
            )
        ) { navBackStackEntry ->
            Contents(
                route = navBackStackEntry,
                navController = navController,
                sightId = navBackStackEntry.arguments?.getInt("sightId")
            )
        }

        composable(
            Route.Feedback.id + "/{sightId}/{rating}",
            arguments = listOf(navArgument("rating") { type = NavType.IntType },
                navArgument("sightId") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            Contents(
                route = navBackStackEntry,
                navController = navController,
                feedbackRating = navBackStackEntry.arguments?.getInt("rating"),
                sightId = navBackStackEntry.arguments?.getInt("sightId")
            )
        }

        composable(Route.Profile.id) { navBackStackEntry ->
            ScaffoldDrawer(
                topBarTitle = "Профиль",
                navController = navController,
                icon = Icons.Default.ArrowBack
            ) {
                Contents(route = navBackStackEntry, navController = navController)
            }
        }
        composable(Route.Settings.id) { navBackStackEntry ->
            ScaffoldDrawer(
                topBarTitle = "Избранное",
                navController = navController,
                icon = Icons.Default.ArrowBack
            ) {
                Contents(route = navBackStackEntry, navController = navController)
            }
        }
        composable(Route.About.id) { navBackStackEntry ->
            ScaffoldDrawer(
                topBarTitle = "О проекте",
                navController = navController,
                icon = Icons.Default.ArrowBack
            ) {
                Contents(route = navBackStackEntry, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Contents(
    route: NavBackStackEntry,
    navController: NavController,
    bottomSheetScaffoldState: BottomSheetScaffoldState? = null,
    localityId: Int? = null,
    latitude: Float? = null,
    longitude: Float? = null,
    sightId: Int? = null,
    feedbackRating: Int? = null
) {
    Crossfade(targetState = route) {
        when (it.destination.route.toString()) {
            Route.StartScreen.id -> StartScreen(navController)
            Route.Filter.id -> FilterScreen(navController)

            Route.SightList.id + "/{localityId}" -> SightList(
                navController,
                localityId = localityId,
                bottomSheetScaffoldState = bottomSheetScaffoldState!!
            )
            Route.SightList.id + "/{latitude}/{longitude}" -> SightList(
                navController,
                latitude = latitude,
                longitude = longitude,
                bottomSheetScaffoldState = bottomSheetScaffoldState!!
            )

            Route.SightDetail.id + "/{sightId}" -> SightDetail(
                navController = navController,
                sightId!!
            )
            Route.Settings.id -> Settings(navController)
            Route.About.id -> About()
            Route.Feedback.id + "/{sightId}/{rating}" -> FeedbackScreen(
                rating = feedbackRating!!,
                sightId = sightId!!,
                navController = navController
            )
            else -> Text("UNKNOWN PAGE")
        }
    }
}