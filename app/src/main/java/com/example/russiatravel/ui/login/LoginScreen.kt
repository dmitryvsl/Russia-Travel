package com.example.russiatravel.ui.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.russiatravel.ui.components.CustomTextField
import com.example.russiatravel.ui.components.FilledButton

@Composable
fun LoginScreen(
    navController: NavController,
    onCreateAccountButtonClick: (ScreenFragment) -> Unit
) {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    val spacerHeight = 20.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            "Авторизуйтесь, чтобы войти",
            style = MaterialTheme.typography.subtitle1.copy(color = ColorBlueDark)
        )
        Spacer(Modifier.height(spacerHeight))

        CustomTextField(
            value = emailValue,
            hintText = "Email",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { emailValue = it },
            icon = Icons.Default.Mail
        )
        Spacer(Modifier.height(14.dp))
        CustomTextField(
            passwordValue,
            hintText = "Пароль",
            icon = Icons.Default.Lock,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { passwordValue = it },
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "Забыли пароль?",
                modifier = Modifier.clickable {  },
                color = Color.Black,
                style = MaterialTheme.typography.button
            )
        }

        Spacer (Modifier.height(spacerHeight))

        FilledButton(
            modifier = Modifier.padding(8.dp),
            text = "Войти",
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlueDark,
                contentColor = Color.White
            ),
        ) {

        }

        Spacer (Modifier.height(spacerHeight))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text ("Нет аккаунта? ", style = MaterialTheme.typography.button.copy(color = Color.Black))
            Text(
                modifier = Modifier.clickable { onCreateAccountButtonClick(ScreenFragment.CreateAccount) },
                text = "Регистрация",
                style = MaterialTheme.typography.button.copy(color = ColorBlueDark))
        }

        Spacer (Modifier.height(spacerHeight))
    }

}
