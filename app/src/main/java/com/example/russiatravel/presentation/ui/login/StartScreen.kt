package com.example.russiatravel.ui.login

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.russiatravel.ui.theme.ColorBlueDark

@Composable
fun StartScreen(navController: NavController) {

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
                    ScreenFragment.Welcome -> WelcomeScreen(navController) {
                        semiCurrentScreen = it
                    }
                    ScreenFragment.CreateAccount -> CreateAccountScreen(
                        navController = navController,
                        onHaveAccountButtonClick = {semiCurrentScreen = it}
                    )
                    ScreenFragment.Login -> LoginScreen(
                        navController = navController,
                        onCreateAccountButtonClick = {semiCurrentScreen = it}
                    )
                }
            }
        }
    }
}

@Composable
private fun updateTransitionData( screenFragment: ScreenFragment): TransitionData {
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

