package com.example.russiatravel.presentation.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.filter.TopBar
import com.example.russiatravel.ui.theme.ColorBlueDark
import kotlinx.coroutines.launch

@Composable
fun ScaffoldDrawer(
    topBarTitle: String = "",
    navController: NavController,
    icon: ImageVector = Icons.Default.Menu,
    showActions: Boolean = false,
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
            Surface(color = ColorBlueDark, modifier = Modifier.height(25.dp)) {}
            TopBar(
                title = topBarTitle,
                icon = icon,
                showActions = showActions,
                onButtonClicked = { if (icon == Icons.Default.Menu) openDrawer() else navController.navigateUp() }
            )
        },
        drawerContent = { DrawerContent(navController) },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        scaffoldState = scaffoldState
    ) { content() }
}