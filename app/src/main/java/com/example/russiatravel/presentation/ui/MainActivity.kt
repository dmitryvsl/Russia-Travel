package com.example.russiatravel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.filter.TopBar
import com.example.russiatravel.ui.filter.FilterScreen
import com.example.russiatravel.ui.login.StartScreen
import com.example.russiatravel.ui.theme.RussiaTravelTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RussiaTravelTheme(darkTheme = false) {
                MyApp()
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MyApp() {
    var isUserLogin by remember { mutableStateOf(SharedPreferences.checkTokenExist()) }

    val scaffoldState = rememberScaffoldState(DrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    var topBarTitle by remember { mutableStateOf("") }

    val openDrawer = {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    if (!isUserLogin) {
        StartScreen(
            userLoggedIn = { isUserLogin = true }
        )
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    title = topBarTitle,
                    onButtonClicked = { openDrawer() }
                )

            },
            drawerContent = { DrawerContent() },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            scaffoldState = scaffoldState
        ) {
            NavGraph { topBarTitle = it }
        }

    }

}




