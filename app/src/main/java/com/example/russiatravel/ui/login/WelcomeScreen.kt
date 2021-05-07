package com.example.russiatravel.ui.login

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.R
import com.example.russiatravel.ui.Route
import com.example.russiatravel.ui.components.FilledButton
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorWhiteDark

@ExperimentalAnimationApi
@Composable
fun WelcomeScreen(
    navController: NavController,
    onButtonClick: (ScreenFragment) -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visible,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Добро пожаловать",
                style = MaterialTheme.typography.subtitle1
            )

            Divider(
                Modifier
                    .width(180.dp)
                    .padding(top = 6.dp, bottom = 40.dp),
                color = ColorWhiteDark,
                thickness = 4.dp
            )

            Row(
                horizontalArrangement = Arrangement.Center
            ) {

                FilledButton(
                    text = "Создать аккаунт",
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = ColorBlueDark
                    ),
                    onClick = { onButtonClick(ScreenFragment.CreateAccount) })

                Spacer(modifier = Modifier.width(20.dp))

                FilledButton(
                    text = "Войти",
                    border = BorderStroke(1.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = ColorBlueDark,
                        contentColor = Color.White,
                    ),
                    onClick = { onButtonClick(ScreenFragment.Login) })
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "или",
                style = MaterialTheme.typography.button.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(14.dp))

            FilledButton(
                text = "Войти как гость",
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = ColorBlueDark,
                    contentColor = Color.White,
                ),
                onClick = { navController.navigate(Route.Filter.id) }
            )
        }
    }

}