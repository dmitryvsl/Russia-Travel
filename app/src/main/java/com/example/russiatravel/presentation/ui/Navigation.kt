package com.example.russiatravel.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.presentation.ui.sight.SightDetail
import com.example.russiatravel.presentation.ui.sight.SightList
import com.example.russiatravel.ui.filter.FilterScreen
import java.io.Serializable

sealed class Route(val id: String) {
    object Filter : Route("filter")
    object SightList : Route("sight_list")
    object SightDetail : Route("sight_detail")
}

@ExperimentalAnimationApi
@Composable
fun NavGraph(changeTitle: (String) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Filter.id) {
        composable(Route.Filter.id) {
            changeTitle("Расположение")
            Contents(it, navController)
        }

        composable(
            route = Route.SightList.id + "/{localityId}",
            arguments = listOf(navArgument("localityId") { type = NavType.IntType })
        ) {
            changeTitle("Достопримечательности")
            Contents(
                route = it,
                navController = navController,
                localityId = it.arguments?.getInt("localityId") ?: 0
            )
        }

        composable(
            route = Route.SightDetail.id + "/{sightId}",
            arguments = listOf(navArgument("sightId") {
                type = NavType.IntType
            })
        ) {
            Contents(
                route = it,
                navController = navController,
                sightId = it.arguments?.getInt("sightId"),
                changeTitle = {title -> changeTitle(title)}
            )
        }
    }

}

@ExperimentalAnimationApi
@Composable
fun Contents(
    route: NavBackStackEntry,
    navController: NavController,
    localityId: Int = 0,
    sightId: Int? = null,
    changeTitle: (String) -> Unit = {}
) {
    Crossfade(targetState = route) {
        when (it.arguments?.getString(KEY_ROUTE)) {
            Route.Filter.id -> FilterScreen(navController)

            Route.SightList.id + "/{localityId}" -> SightList(navController, localityId = localityId)

            Route.SightDetail.id + "/{sightId}" -> SightDetail(navController = navController, sightId!!, changeTitle)
            else -> Text("UNKNOWN PAGE")
        }
    }
}