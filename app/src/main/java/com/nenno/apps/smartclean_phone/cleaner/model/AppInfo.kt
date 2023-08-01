package com.nenno.apps.smartclean_phone.cleaner.model

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.vector.ImageVector

data class AppInfo(
    val appName: String,
    val packageName: String,
    val appSize: Long,
    val appIcon: Drawable
)
