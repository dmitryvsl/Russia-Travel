package com.example.russiatravel.presentation.ui.drawer

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.filter.TopBar
import kotlinx.coroutines.launch

@Composable
fun ScaffoldDrawer(
    topBarTitle: String = "",
    navController: NavController,
    icon: ImageVector  = Icons.Default.Menu,
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberScaffoldState(DrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    val openDrawer = {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = topBarTitle,
                icon = icon,
                onButtonClicked = { if (icon == Icons.Default.Menu) openDrawer() else navController.navigateUp() }
            )
        },
        drawerContent = { DrawerContent(navController) },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        scaffoldState = scaffoldState
    ){ content() }
}