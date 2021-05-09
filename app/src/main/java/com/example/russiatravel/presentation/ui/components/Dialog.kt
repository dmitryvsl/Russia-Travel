package com.example.russiatravel.presentation.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorWhiteDark

@Composable
fun ErrorDialog(message: String,setShowDialog: (Boolean) -> Unit ){
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text ("Ошибка авторизации!", style = MaterialTheme.typography.body1.copy(color = Color.Black)) },
        confirmButton = {
            Button (
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorBlueDark),
                onClick = {setShowDialog(false)}
            ){
                Text("ОК", style = MaterialTheme.typography.body1.copy(ColorWhiteDark))
            }
        },
        text = { Text (message, style = MaterialTheme.typography.body1.copy(color = Color.Black)) }
    )
}

@Composable
fun LoadingDialog(){
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        text = { CircularProgressIndicator() },
        buttons = {}
    )
}

