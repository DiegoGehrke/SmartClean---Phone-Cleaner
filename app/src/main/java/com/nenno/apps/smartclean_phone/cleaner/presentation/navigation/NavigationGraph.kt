package com.nenno.apps.smartclean_phone.cleaner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery.ManageBatteryScreen
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery.ManageBatteryViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery_usage.BatteryUsageScreen
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery_usage.BatteryUsageViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.home.HomeScreen
import com.nenno.apps.smartclean_phone.cleaner.presentation.home.HomeScreenViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.manage_apps.ManageAppsScreen
import com.nenno.apps.smartclean_phone.cleaner.presentation.manage_apps.ManageAppsViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.start.StartScreen
import com.nenno.apps.smartclean_phone.cleaner.presentation.start.StartScreenViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startScreenViewModel: StartScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    manageBatteryViewModel: ManageBatteryViewModel,
    manageAppsViewModel: ManageAppsViewModel,
    batteryUsageViewModel: BatteryUsageViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.ManageBattery.route
    ) {
        composable(
            NavigationScreen.Start.route
        ) {
            StartScreen(startScreenViewModel, navController)
        }

        composable(
            NavigationScreen.Home.route
        ) {
            HomeScreen(homeScreenViewModel, navController)
        }

        composable(
            NavigationScreen.ManageApps.route
        ) {
            ManageAppsScreen(manageAppsViewModel, navController)
        }

        composable(
            NavigationScreen.ManageBattery.route
        ) {
            ManageBatteryScreen(manageBatteryViewModel, navController)
        }

        composable(
            NavigationScreen.BatteryUsage.route
        ) {
            BatteryUsageScreen(batteryUsageViewModel, navController)
        }
    }
}