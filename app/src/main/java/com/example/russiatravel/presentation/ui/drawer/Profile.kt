package com.example.russiatravel.presentation.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.russiatravel.cache.SharedPreferences
import com.example.russiatravel.presentation.ui.components.NothingHere

@Composable
fun Profile() {
    Column {
        NothingHere()
        Button(
            onClick = { SharedPreferences.removeToken() }
        ){
            Text("Выйти")
        }
    }
}