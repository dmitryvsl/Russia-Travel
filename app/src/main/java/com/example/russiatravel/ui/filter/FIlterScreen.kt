package com.example.russiatravel.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    ModalDrawer(
        drawerContent = { Drawer() },
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
    ) {
        TopBar("РАсположение достопримечательности") {
            openDrawer()
        }
    }
}

@Composable
fun TopBar(title: String, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(Icons.Default.Menu, null)
            }
        }
    )
}

@Composable
fun Drawer() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)) {
        Text("Drawer")
    }
}