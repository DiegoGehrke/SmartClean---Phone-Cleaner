package com.nenno.apps.smartclean_phone.cleaner.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.nenno.apps.smartclean_phone.cleaner.R

object RobotoFontFamily {
    val robotoFontFamily = FontFamily(
            Font(R.font.roboto_black, FontWeight.Black),
            Font(R.font.roboto_bold, FontWeight.Bold),
            Font(R.font.roboto_light, FontWeight.Light),
            Font(R.font.roboto_medium, FontWeight.Medium),
            Font(R.font.roboto_regular, FontWeight.Normal)
        )
}