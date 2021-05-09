package com.example.russiatravel.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.russiatravel.R

// Set of Material typography styles to start with
val Typography = Typography(
    subtitle1 = TextStyle(
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.didactfothic,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(
            Font(
                resId = R.font.ptsans_regular
            )
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.comfortaa_light,
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
