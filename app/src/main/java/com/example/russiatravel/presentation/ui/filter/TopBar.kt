package com.example.russiatravel.presentation.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.russiatravel.ui.theme.ColorBlueDark

@Composable
fun TopBar(
    title: String,
    icon: ImageVector,
    showActions: Boolean,
    onButtonClicked: () -> Unit,
    onActionClicked: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(top = 24.dp),
        title = {
            Text(text = title, style = MaterialTheme.typography.subtitle1)
        },
        backgroundColor = ColorBlueDark,
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(icon, null, tint = Color.White)
            }
        },
        actions = {
            if (showActions){
                IconButton(onClick = { onActionClicked() }) {
                    Icon(Icons.Default.FilterAlt, null, tint = Color.White)
                }
            }
        }
    )


}

