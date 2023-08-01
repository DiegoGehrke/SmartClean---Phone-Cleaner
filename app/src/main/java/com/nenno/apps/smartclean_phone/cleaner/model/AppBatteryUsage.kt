package com.nenno.apps.smartclean_phone.cleaner.model

data class AppBatteryUsage(
    val appName: String,
    val packageName: String,
    val usagePercentage: Double
)

