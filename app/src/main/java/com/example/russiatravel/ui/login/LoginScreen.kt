package com.example.russiatravel.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.russiatravel.R
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorBrown
import com.example.russiatravel.ui.theme.ColorWhiteDark
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen(
    navController: NavController,
    onCreateAccountButtonClick: (ScreenFragment) -> Unit
) {
    var emailValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Авторизуйтесь, чтобы войти",
            style = TextStyle(
                color = ColorBlueDark,
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

        Spacer(Modifier.height(20.dp))


        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = emailValue,
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ColorWhiteDark,
                textColor = ColorBrown,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text("Email") },
            trailingIcon = {
                Icon(Icons.Default.Email, null)
            },
            onValueChange = { emailValue = it },
        )
    }

}
