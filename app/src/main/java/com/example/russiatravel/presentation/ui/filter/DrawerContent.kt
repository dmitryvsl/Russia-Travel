package com.example.russiatravel.presentation.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russiatravel.cache.SharedPreferences

@Composable
fun DrawerContent() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)) {
        Text("Drawer")
        Button(onClick = { SharedPreferences.removeToken() }) {
            Text("Выйти")
        }
    }
}