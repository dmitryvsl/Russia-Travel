package com.example.russiatravel.presentation.ui.filter

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.russiatravel.ui.theme.ColorBlueDark

@Composable
fun TopBar(
    title: String,
    icon: ImageVector,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.subtitle1)
        },
        backgroundColor = ColorBlueDark,
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(icon, null, tint = Color.White)
            }
        }
    )
}

