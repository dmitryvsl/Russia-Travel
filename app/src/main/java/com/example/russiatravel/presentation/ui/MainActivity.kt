package com.example.russiatravel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.russiatravel.ui.login.StartScreen
import com.example.russiatravel.ui.theme.RussiaTravelTheme
import dagger.hilt.android.AndroidEntryPoint

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
fun MyApp(){
    var isUserLogin by remember { mutableStateOf(false)}
    Scaffold {
        if (isUserLogin){
            NavGraph()
        }else{
            StartScreen(
                userLoggedIn =  { isUserLogin = true }
            )
        }
    }
}




