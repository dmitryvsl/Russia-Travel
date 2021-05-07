package com.example.russiatravel.ui.login

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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.russiatravel.ui.Route
import com.example.russiatravel.ui.components.CustomTextField
import com.example.russiatravel.ui.components.FilledButton
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorWhiteDark
import java.util.regex.Pattern

@Composable
fun CreateAccountScreen(
    navController: NavController,
    onHaveAccountButtonClick: (ScreenFragment) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        var nameValue by remember { mutableStateOf("") }
        var emailValue by remember { mutableStateOf("") }
        var passwordValue by remember { mutableStateOf("") }
        var confirmPasswordValue by remember { mutableStateOf("") }

        var nameError by remember { mutableStateOf(false) }
        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }
        var confirmPasswordError by remember { mutableStateOf(false) }

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
        ) { nameValue = it }

        ContentSpacerOrErrorText(text = "Пустое поле!", hasError = nameError)

        CustomTextField(
            value = emailValue,
            hintText = "Ваш E-mail",
            icon = Icons.Filled.Email,
            isError = emailError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        ) { emailValue = it }

        ContentSpacerOrErrorText(text = "Неправильный E-mail", hasError = emailError)

        CustomTextField(
            value = passwordValue,
            hintText = "Введите пароль",
            icon = Icons.Filled.Lock,
            isError = passwordError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        ) { passwordValue = it }

        ContentSpacerOrErrorText(text = "Пустое поле!", hasError = passwordError)

        CustomTextField(
            value = confirmPasswordValue,
            hintText = "Повторите пароль",
            icon = Icons.Filled.LockOpen,
            isError = confirmPasswordError,
            visualTransformation = PasswordVisualTransformation(),
        ) { confirmPasswordValue = it }

        ContentSpacerOrErrorText(
            text = if (passwordValue.isBlank()) "Пустое поле!" else "Пароли не совпадают!",
            hasError = confirmPasswordError
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
                navController.navigate(Route.Filter.id)
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Уже есть аккаунт? ",
                style = MaterialTheme.typography.button.copy(color = Color.Black)
            )
            Text(
                modifier = Modifier.clickable { onHaveAccountButtonClick(ScreenFragment.Login) },
                text = "Войти",
                style = MaterialTheme.typography.button.copy(color = ColorBlueDark)
            )
        }
    }
}

@Composable
fun ContentSpacerOrErrorText(text: String, hasError: Boolean, spacerHeight: Dp = 20.dp) {
    if (hasError) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(
                    color = Color.Red,
                    fontSize = 12.sp
                ),
            )
        }

        Spacer(Modifier.height(spacerHeight - 13.dp))
    } else Spacer(Modifier.height(spacerHeight))
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
