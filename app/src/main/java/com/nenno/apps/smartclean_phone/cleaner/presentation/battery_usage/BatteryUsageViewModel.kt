package com.nenno.apps.smartclean_phone.cleaner.presentation.battery_usage

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nenno.apps.smartclean_phone.cleaner.model.AppBatteryUsage
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BatteryUsageViewModel @Inject constructor() : ViewModel() {

}