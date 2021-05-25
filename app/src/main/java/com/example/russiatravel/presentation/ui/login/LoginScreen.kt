package com.example.russiatravel.ui.login

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russiatravel.ui.theme.ColorBlueDark
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.navigate
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.components.ErrorDialog
import com.example.russiatravel.presentation.ui.components.LoadingDialog
import com.example.russiatravel.ui.components.CustomTextField
import com.example.russiatravel.ui.components.FilledButton
import com.example.russiatravel.viewModel.StartScreenViewModel

@SuppressLint("RestrictedApi")
@OptIn( ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    onCreateAccountButtonClick: (ScreenFragment) -> Unit,
    viewModel: StartScreenViewModel = hiltNavGraphViewModel()
) {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    val spacerHeight = 20.dp
    var isScreenVisible by remember { mutableStateOf(true) }

    if (viewModel.loadError.value != ""){
        ErrorDialog (viewModel.loadError.value) {viewModel.loadError.value = ""} // Показывает окно ошибки
    }
    if (viewModel.isLoading.value){
        LoadingDialog() // Показывает окно загрузки
    }
    if (viewModel.token.value != ""){
        navController.backStack.removeLast()
        navController.navigate(Route.Filter.id)
    }

    AnimatedVisibility(visible = isScreenVisible,
        initiallyVisible = false,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
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
            ) {
                Text(
                    text = "Забыли пароль?",
                    modifier = Modifier.clickable { },
                    color = Color.Black,
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(Modifier.height(spacerHeight))

            FilledButton(
                modifier = Modifier.padding(8.dp),
                text = "Войти",
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorBlueDark,
                    contentColor = Color.White
                ),
            ) {
                viewModel.authUser(emailValue, passwordValue)
            }

            Spacer(Modifier.height(spacerHeight))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Нет аккаунта? ",
                    style = MaterialTheme.typography.button.copy(color = Color.Black)
                )
                Text(
                    modifier = Modifier.clickable {
                        isScreenVisible = false
                        onCreateAccountButtonClick(ScreenFragment.CreateAccount)
                    },
                    text = "Регистрация",
                    style = MaterialTheme.typography.button.copy(color = ColorBlueDark)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "или",
                style = MaterialTheme.typography.button.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(14.dp))

            FilledButton(
                text = "Войти как гость",
                border = BorderStroke(1.dp, Color.Transparent),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = ColorBlueDark,
                    contentColor = Color.White,
                ),
                onClick = {
                    navController.backStack.removeLast()
                    navController.navigate(Route.Filter.id)
                }
            )

            Spacer(Modifier.height(spacerHeight))

        }
    }
}

