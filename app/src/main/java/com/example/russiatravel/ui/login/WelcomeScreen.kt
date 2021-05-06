package com.example.russiatravel.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorWhiteDark

@Composable
fun WelcomeScreen(
    navController: NavController,
    onButtonClick: (ScreenFragment) -> Unit
){
    val buttonTextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.cousine_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Добро пожаловать",
            style = TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.didactfothic,
                        weight = FontWeight.Bold,
                        style = FontStyle.Normal
                    )
                )
            )
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
            Button(
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = ColorBlueDark
                ),
                onClick = { onButtonClick (ScreenFragment.CreateAccount) }
            ) {
                Text(
                    "Создать аккаунт",
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorBlueDark,
                    contentColor = Color.White
                ),
                onClick = { onButtonClick (ScreenFragment.Login) }
            ) {
                Text(
                    "Войти",
                    style = MaterialTheme.typography.button
                )

            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            "или",
            style = buttonTextStyle.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlueDark,
                contentColor = Color.White
            ),
            onClick = { navController.navigate(Route.Filter.id)  }
        ) {
            Text("Войти как гость",style = MaterialTheme.typography.button)
        }
    }
}