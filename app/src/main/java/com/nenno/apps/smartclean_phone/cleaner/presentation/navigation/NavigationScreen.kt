package com.nenno.apps.smartclean_phone.cleaner.presentation.navigation

sealed class NavigationScreen (val route: String) {
    object Home: NavigationScreen(route = "homeScreen")
    object Start: NavigationScreen(route = "startScreen")
    object ManageApps: NavigationScreen(route = "manageAppsScreen")
    object ManageBattery: NavigationScreen(route = "manageBattery")
    object BatteryUsage: NavigationScreen(route = "batteryUsage")
}