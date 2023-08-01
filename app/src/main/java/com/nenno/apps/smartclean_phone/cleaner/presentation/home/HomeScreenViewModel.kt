package com.nenno.apps.smartclean_phone.cleaner.presentation.home

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nenno.apps.smartclean_phone.cleaner.util.DeviceMemory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel : ViewModel() {
    private val _totalSpace = MutableStateFlow(0F)
    val totalSpace: StateFlow<Float> get() = _totalSpace

    private val _openDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> get() = _openDialog

    private val _readStoragePermissionGranted = MutableLiveData<Boolean>()
    val readStoragePermissionGranted: LiveData<Boolean>
        get() = _readStoragePermissionGranted

    private val _occupiedSpace = MutableStateFlow(0F)
    val occupiedSpace: StateFlow<Float> get() = _occupiedSpace

    private val _freeSpace = MutableStateFlow(0F)
    val freeSpace: StateFlow<Float> get() = _freeSpace

    fun getSmartphoneStorageData() {
        _totalSpace.value = DeviceMemory.internalStorageSpace
        _occupiedSpace.value = DeviceMemory.internalUsedSpace
        _freeSpace.value = DeviceMemory.internalFreeSpace
    }

    fun checkReadStoragePermission(context: Context) {
        val permissionStatus = context.checkSelfPermission(READ_EXTERNAL_STORAGE) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED

        _readStoragePermissionGranted.value = permissionStatus
    }

    fun requestReadStoragePermission(activity: ComponentActivity) {
        if (_readStoragePermissionGranted.value != true) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    READ_EXTERNAL_STORAGE
                )
            ) {
                _openDialog.value = true
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    fun onReadStoragePermissionResult(granted: Boolean) {
        _readStoragePermissionGranted.value = granted
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
