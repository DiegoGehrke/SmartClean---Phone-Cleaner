package com.nenno.apps.smartclean_phone.cleaner.presentation.manage_apps

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.StatFs
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nenno.apps.smartclean_phone.cleaner.model.AppInfo
import java.io.File
import kotlin.math.log10
import kotlin.math.pow


class ManageAppsViewModel : ViewModel() {

    private val _installedApps = MutableLiveData<List<AppInfo>>()
    val installedApps: LiveData<List<AppInfo>> get() = _installedApps

    fun loadInstalledApps(context: Context) {
        val packageManager = context.packageManager
        val appsList = mutableListOf<AppInfo>()

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfo: List<ResolveInfo> = packageManager.queryIntentActivities(mainIntent, 0)
        for (resolveInfo in resolveInfo) {
            val appName = resolveInfo.loadLabel(packageManager).toString()
            val packageName = resolveInfo.activityInfo.packageName
            val appSize = getAppSize(context, packageName)
            val appIcon = resolveInfo.loadIcon(packageManager)
            appsList.add(AppInfo(appName, packageName, appSize, appIcon))
        }

        _installedApps.value = appsList
    }

    private fun getAppSize(context: Context, packageName: String): Long {
        val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
        val sourceDir = packageInfo.applicationInfo.sourceDir
        val file = File(sourceDir)
        return file.length()
    }

    fun formatFileSize(size: Long): String {
        if (size <= 0) return "0"

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return String.format("%.2f %s", size / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
    }
}
