package com.example.russiatravel.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.presentation.ui.Route
import com.example.russiatravel.presentation.ui.components.ErrorDialog
import com.example.russiatravel.presentation.ui.components.LoadingDialog
import com.example.russiatravel.ui.components.CustomTextField
import com.example.russiatravel.ui.components.FilledButton
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.viewModel.UserViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreateAccountScreen(
    navController: NavController,
    onHaveAccountButtonClick: (ScreenFragment) -> Unit,
    viewModel: UserViewModel = hiltNavGraphViewModel()
) {
    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var isScreenVisible by remember { mutableStateOf(true) }

    if (viewModel.loadError.value != ""){
        ErrorDialog (viewModel.loadError.value) {viewModel.loadError.value = ""} // Показывает окно ошибки
    }
    if (viewModel.isLoading.value){
        LoadingDialog() // Показывает окно загрузки
    }
    if (viewModel.token.value != ""){
        navController.backQueue.removeFirst()
        navController.navigate(Route.Filter.id)
    }

    AnimatedVisibility(
        isScreenVisible,
        initiallyVisible = false,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "Создать аккаунт",
                style = MaterialTheme.typography.subtitle1.copy(color = ColorBlueDark)
            )
            Divider(
                Modifier
                    .width(180.dp)
                    .padding(top = 6.dp, bottom = 40.dp),
                color = ColorBlueDark,
                thickness = 4.dp
            )
            CustomTextField(
                value = nameValue,
                hintText = "Ваше имя",
                icon = Icons.Filled.AccountCircle,
                isError = nameError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            ) { if (it.length <= 30) nameValue = it }

            CharacterCountOrErrorText(
                text = "Пустое поле!",
                hasError = nameError,
                currentCount = nameValue.length,
                totalCount = 30
            )

            CustomTextField(
                value = emailValue,
                hintText = "Ваш E-mail",
                icon = Icons.Filled.Email,
                isError = emailError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            ) { if (it.length <= 50) emailValue = it }

            CharacterCountOrErrorText(
                text = "Неправильный E-mail",
                hasError = emailError,
                currentCount = emailValue.length,
                totalCount = 50
            )

            CustomTextField(
                value = passwordValue,
                hintText = "Введите пароль",
                icon = Icons.Filled.Lock,
                isError = passwordError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            ) { if (it.length <= 30) passwordValue = it }

            CharacterCountOrErrorText(
                text = "Пустое поле!",
                hasError = passwordError,
                currentCount = passwordValue.length,
                totalCount = 30
            )

            CustomTextField(
                value = confirmPasswordValue,
                hintText = "Повторите пароль",
                icon = Icons.Filled.LockOpen,
                isError = confirmPasswordError,
                visualTransformation = PasswordVisualTransformation(),
            ) { if (it.length <= 30) confirmPasswordValue = it }

            CharacterCountOrErrorText(
                text = if (passwordValue.isBlank()) "Пустое поле!" else "Пароли не совпадают!",
                hasError = confirmPasswordError,
                currentCount = confirmPasswordValue.length,
                totalCount = 30
            )

            FilledButton(
                text = "Регистрация",
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = ColorBlueDark,
                )
            ) {
                nameError = nameValue.isBlank()
                emailError = emailValue.isBlank() || !isEmailValid(emailValue)
                passwordError = passwordValue.isBlank()
                confirmPasswordError =
                    confirmPasswordValue.isBlank() || passwordValue != confirmPasswordValue

                if (!nameError && !emailError && !passwordError && !confirmPasswordError) {
                    viewModel.createAccount(nameValue, emailValue, passwordValue)

                }
            }

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Уже есть аккаунт? ",
                    style = MaterialTheme.typography.button.copy(color = Color.Black)
                )
                Text(
                    modifier = Modifier.clickable {
                        isScreenVisible = true
                        onHaveAccountButtonClick(ScreenFragment.Login)
                    },
                    text = "Войти",
                    style = MaterialTheme.typography.button.copy(color = ColorBlueDark)
                )
            }
        }
    }
}

@Composable
fun CharacterCountOrErrorText(
    text: String,
    hasError: Boolean,
    spacerHeight: Dp = 20.dp,
    currentCount: Int,
    totalCount: Int
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (hasError) {
            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(
                    color = Color.Red,
                    fontSize = 12.sp
                ),
            )
        } else Spacer(Modifier.height(spacerHeight))
        Text(
            text = "$currentCount/$totalCount",
            style = MaterialTheme.typography.button.copy(color = ColorBlueDark)
        )
    }


}



fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
