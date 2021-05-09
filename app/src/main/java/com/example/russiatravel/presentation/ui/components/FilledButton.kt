package com.example.russiatravel.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.russiatravel.ui.login.ScreenFragment
import com.example.russiatravel.ui.theme.ColorBlueDark

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    border:BorderStroke? = null,
    onClick: () -> Unit
){
    Button(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        colors = colors,
        border = border,
        onClick = { onClick () }
    ) {
        Text(
            text,
            style = MaterialTheme.typography.button
        )
    }
}