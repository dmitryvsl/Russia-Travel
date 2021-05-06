package com.example.russiatravel.ui.login

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CreateAccountScreen(
    navController: NavController,
    onHaveAccountButtonClick: (ScreenFragment) -> Unit
    ){
    Button(onClick = { onHaveAccountButtonClick (ScreenFragment.Login) }) {
        Text( " Have Account")
    }
}