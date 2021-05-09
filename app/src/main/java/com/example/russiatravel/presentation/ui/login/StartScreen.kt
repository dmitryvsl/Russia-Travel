package com.example.russiatravel.ui.login

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.R
import com.example.russiatravel.ui.theme.ColorBlueDark

@ExperimentalAnimationApi
@Composable
fun StartScreen(
    userLoggedIn: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(ScreenFragment.Welcome) }
    var semiCurrentScreen: ScreenFragment by remember { mutableStateOf(ScreenFragment.Welcome) }
    val transitionData = updateTransitionData(semiCurrentScreen)
    val surfaceAnimationOnScreenChange by animateDpAsState(
        targetValue = transitionData.size ,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        finishedListener = {
            currentScreen = semiCurrentScreen
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = transitionData.bgColor),
        verticalArrangement = Arrangement.Bottom
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(surfaceAnimationOnScreenChange),
            color = transitionData.surfaceColor,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ) {
            if (semiCurrentScreen == currentScreen) {
                when (currentScreen) {
                    ScreenFragment.Welcome -> WelcomeScreen(userLoggedIn) {
                        semiCurrentScreen = it
                    }
                    ScreenFragment.CreateAccount -> CreateAccountScreen(
                        onUserLogin =  userLoggedIn,
                        onHaveAccountButtonClick = {semiCurrentScreen = it}
                    )
                    ScreenFragment.Login -> LoginScreen(
                        onUserLogin = userLoggedIn,
                        onCreateAccountButtonClick = {semiCurrentScreen = it}
                    )
                }
            }
        }
    }
}

@Composable
private fun updateTransitionData(screenFragment: ScreenFragment): TransitionData {
    val transition = updateTransition(screenFragment, label = "start screen animation")
    val bgColor = transition.animateColor(label = "bg color transition") { state ->
        when (state) {
            ScreenFragment.Welcome -> Color.White
            ScreenFragment.CreateAccount, ScreenFragment.Login -> ColorBlueDark
        }
    }
    val surfaceColor = transition.animateColor(label = "surface color animation") { state ->
        when (state) {
            ScreenFragment.Welcome -> ColorBlueDark
            ScreenFragment.CreateAccount, ScreenFragment.Login -> Color.White
        }
    }
    val size = transition.animateDp(label = "surface height animation") { state ->
        when (state) {
            ScreenFragment.Welcome -> 300.dp
            ScreenFragment.CreateAccount -> 600.dp
            ScreenFragment.Login -> 500.dp
        }
    }
    return remember(transition) { TransitionData(bgColor, surfaceColor, size) }
}

enum class ScreenFragment {
    Welcome, CreateAccount, Login
}

private class TransitionData(
    bgColor: State<Color>,
    surfaceColor: State<Color>,
    size: State<Dp>
) {
    val bgColor by bgColor
    val surfaceColor by surfaceColor
    val size by size
}

