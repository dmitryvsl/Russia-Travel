package com.example.russiatravel.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russiatravel.R
import com.example.russiatravel.ui.theme.ColorLightGray

// Set of Material typography styles to start with
val Typography = Typography(
    subtitle1 = TextStyle(
        color = Color.Black,
        fontSize = 24.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.roboto_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    subtitle2 = TextStyle(
        color = Color.Black,
        fontSize = 24.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.roboto_regular,
                style = FontStyle.Normal
            )
        )
    ),
    body1 = TextStyle(
        color = ColorLightGray,
        fontFamily = FontFamily(
            Font(
                resId = R.font.opensans_regular
            )
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.opensans_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        )
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
