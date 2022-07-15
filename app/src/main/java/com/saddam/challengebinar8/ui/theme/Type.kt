package com.saddam.challengebinar8.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.saddam.challengebinar8.R

// Set of Material typography styles to start with
private val Cerapro = FontFamily(
    Font(R.font.cerapro_bold, FontWeight.Bold),
    Font(R.font.cerapro, FontWeight.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Cerapro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Cerapro,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
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