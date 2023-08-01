package com.nenno.apps.smartclean_phone.cleaner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery.ManageBatteryViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.battery_usage.BatteryUsageViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.home.HomeScreenViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.manage_apps.ManageAppsViewModel
import com.nenno.apps.smartclean_phone.cleaner.presentation.navigation.NavigationGraph
import com.nenno.apps.smartclean_phone.cleaner.presentation.start.StartScreenViewModel
import com.nenno.apps.smartclean_phone.cleaner.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val startScreenViewModel = viewModel<StartScreenViewModel>()
                val homeScreenViewModel = viewModel<HomeScreenViewModel>()
                val manageBatteryViewModel = viewModel<ManageBatteryViewModel>()
                val manageAppsViewModel = viewModel<ManageAppsViewModel>()
                val batteryUsageViewModel = viewModel<BatteryUsageViewModel>()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    NavigationGraph(
                        navController = navHostController,
                        startScreenViewModel = startScreenViewModel,
                        homeScreenViewModel = homeScreenViewModel,
                        manageBatteryViewModel = manageBatteryViewModel,
                        manageAppsViewModel = manageAppsViewModel,
                        batteryUsageViewModel = batteryUsageViewModel
                    )
                }
            }
        }
    }

}
