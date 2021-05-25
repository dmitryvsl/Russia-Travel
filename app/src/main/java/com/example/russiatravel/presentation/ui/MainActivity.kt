package com.example.russiatravel.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.russiatravel.R
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.NavGraph
import com.example.russiatravel.presentation.ui.filter.DrawerContent
import com.example.russiatravel.presentation.ui.filter.TopBar
import com.example.russiatravel.ui.login.StartScreen
import com.example.russiatravel.ui.theme.RussiaTravelTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white);

        setContent {
            ProvideWindowInsets (consumeWindowInsets = false) {
                RussiaTravelTheme(darkTheme = false) {
                    NavGraph()
                }
            }
        }
    }
}



