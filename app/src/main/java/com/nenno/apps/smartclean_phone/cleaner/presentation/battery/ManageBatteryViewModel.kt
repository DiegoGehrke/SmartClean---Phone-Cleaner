package com.nenno.apps.smartclean_phone.cleaner.presentation.battery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ManageBatteryViewModel @Inject constructor() : ViewModel() {
    private var mPowerProfile: Any? = null
    private val _batteryChargeLevel: MutableStateFlow<Float> = MutableStateFlow(0f)
    val batteryChargeLevel: MutableStateFlow<Float> get() = _batteryChargeLevel

    private val _isCharging: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCharging: MutableStateFlow<Boolean> get() = _isCharging

    private val _batteryHealth: MutableStateFlow<Int> = MutableStateFlow(0)
    val batteryHealth: MutableStateFlow<Int> get() = _batteryHealth

    fun getBatteryChargeLevel(context: Context): Float {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }
        val batteryPct: Float = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }?: 0f
        return batteryPct
    }

    fun checkIfIsChargingOrNot(context: Context): Boolean {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return status == BatteryManager.BATTERY_STATUS_CHARGING
    }

    fun getBatteryHealth(context: Context): String {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }
        val deviceHealth = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)

        val healthMap = hashMapOf(
            BatteryManager.BATTERY_HEALTH_COLD to "Cold",
            BatteryManager.BATTERY_HEALTH_DEAD to "Dead",
            BatteryManager.BATTERY_HEALTH_GOOD to "Good",
            BatteryManager.BATTERY_HEALTH_OVERHEAT to "Overheat",
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE to "Over voltage",
            BatteryManager.BATTERY_HEALTH_UNKNOWN to "Health Unknown",
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE to "Unspecified Failure"
        )

        return healthMap[deviceHealth] ?: "Health Unknown"
    }

    @SuppressLint("PrivateApi")
    fun estimateRemainingTimeUntilChargeLevelGoToZero(context: Context): String {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }

        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val batteryPct: Float = level * 100 / scale.toFloat()

        val chargingStatus: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = chargingStatus == BatteryManager.BATTERY_STATUS_CHARGING || chargingStatus == BatteryManager.BATTERY_STATUS_FULL

        val batteryHealth: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1
        val isBatteryGood: Boolean = batteryHealth == BatteryManager.BATTERY_HEALTH_GOOD

        // Calculate battery capacity in mAh
        mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
            .getConstructor(Context::class.java)
            .newInstance(context)

        val batteryCapacity = Class
            .forName(POWER_PROFILE_CLASS)
            .getMethod("getBatteryCapacity")
            .invoke(mPowerProfile) as Double

        // Average consumption rate per minute
        val averageConsumptionPerMinute: Float = if (isCharging) 0.0f else if (isBatteryGood) 0.5f else 1.0f

        // Calculate remaining time in minutes
        val remainingTimeInMinutes: Int = ((batteryCapacity / 100) * batteryPct / averageConsumptionPerMinute).toInt()

        // Format the remaining time in hours and minutes
        val hours = remainingTimeInMinutes / 60
        val minutes = remainingTimeInMinutes % 60

        return "~$hours hours and $minutes minutes"
    }

    fun getCurrentBatteryTemperature(context: Context): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }

        val batteryTemperature = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_TEMPERATURE,
            0
        )

        return if (batteryTemperature != null) {
            (batteryTemperature / 10)
        } else {
            0
        }
    }

    @SuppressLint("PrivateApi")
    fun getBatteryCapacity(context: Context): String {
        // Calculate battery capacity in mAh
        mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
            .getConstructor(Context::class.java)
            .newInstance(context)

        val batteryCapacity = Class
            .forName(POWER_PROFILE_CLASS)
            .getMethod("getBatteryCapacity")
            .invoke(mPowerProfile) as Double

        return batteryCapacity.toString()
    }

    fun openBatterySettings(context: Context) {
        val batterySaverIntent = Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS)
        context.startActivity(batterySaverIntent)
    }
    companion object {
        private const val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
    }
}