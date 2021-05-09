package com.example.russiatravel.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.example.russiatravel.ui.theme.ColorBlueDark
import com.example.russiatravel.ui.theme.ColorBrown
import com.example.russiatravel.ui.theme.ColorWhiteDark
import kotlin.math.max

@Composable
fun CustomTextField(
    value: String,
    hintText: String,
    icon: ImageVector,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    onValueChange: (String) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = when (keyboardOptions.imeAction) {
        ImeAction.Done -> KeyboardActions(onDone = { localFocusManager.clearFocus() })
        ImeAction.Next -> KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) })
        else -> KeyboardActions()
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isError) Color.Red else Color.Transparent,
                RoundedCornerShape(10.dp)
            ),
        value = value,
        shape = RoundedCornerShape(10.dp),
        textStyle = MaterialTheme.typography.button,
        visualTransformation = visualTransformation,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = { Text(hintText, style = MaterialTheme.typography.button.copy(color = ColorBrown)) },
        trailingIcon = { Icon(icon, "", tint = ColorBlueDark) },
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = ColorWhiteDark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
    )

}
