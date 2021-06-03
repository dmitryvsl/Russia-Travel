package com.example.russiatravel.presentation.ui.sight

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.RussiaTravelApplication
import com.example.russiatravel.presentation.ui.components.ErrorDialog
import com.example.russiatravel.presentation.ui.components.LoadingDialog
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.viewModel.FeedbackStatus
import com.example.russiatravel.viewModel.SightViewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun FeedbackScreen(
    navController: NavController,
    viewModel: SightViewModel = hiltNavGraphViewModel(),
    rating: Int,
    sightId: Int
) {
    var textValue by remember { mutableStateOf("") }
    var selectedRating by remember { mutableStateOf(rating) }
    val localFocusManager = LocalFocusManager.current
    val requestStatus = viewModel.addFeedbackState.observeAsState()
    when {
        viewModel.isLoading.value -> {
            localFocusManager.clearFocus()
            LoadingDialog()
        }

        requestStatus.value == FeedbackStatus.ERROR -> Toast.makeText(
            RussiaTravelApplication.context,
            "Произошла ошибка при добавлении отзыва",
            Toast.LENGTH_LONG
        ).show()

        requestStatus.value == FeedbackStatus.SUCCESS -> navController.navigateUp()

        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    modifier = Modifier.height(49.dp),
                    title = {
                        Text(
                            "Написать отзыв",
                            style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
                        )
                    },
                    backgroundColor = ColorBlueDark
                )
                Spacer(Modifier.height(6.dp))
                ClickableRatingBar(selectedRating) {
                    selectedRating = it
                }
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ColorBlueDark,
                        unfocusedBorderColor = ColorBlueDark,
                        textColor = Color.Black,
                        disabledTextColor = Color.Black
                    ),
                    maxLines = 5,
                    value = textValue,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                    onValueChange = { textValue = it }
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (textValue.isEmpty()) {
                            Toast.makeText(
                                RussiaTravelApplication.context,
                                "Нельзя оставить пустой отзыв",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.addFeedback(
                                sightId = sightId,
                                rating = rating,
                                token = SharedPreferences.loadToken()!!,
                                feedback = textValue
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorBlueDark
                    )
                ) {
                    Text("Отправить", style = MaterialTheme.typography.button, color = Color.White)
                }
            }
        }
    }


}