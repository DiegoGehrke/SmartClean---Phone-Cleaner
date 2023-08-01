package com.nenno.apps.smartclean_phone.cleaner.util

import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.File

object DeviceMemory {

    val internalStorageSpace: Float
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            val totalBlocks = statFs.blockCountLong
            val blockSize = statFs.blockSizeLong
            return (totalBlocks * blockSize) / 1048576f
        }

    val internalFreeSpace: Float
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            val availableBlocks = statFs.availableBlocksLong
            val blockSize = statFs.blockSizeLong
            return (availableBlocks * blockSize) / 1048576f
        }

    val internalUsedSpace: Float
        get() {
            val statFs = StatFs(Environment.getDataDirectory().absolutePath)
            val totalBlocks = statFs.blockCountLong
            val availableBlocks = statFs.availableBlocksLong
            val blockSize = statFs.blockSizeLong
            val totalSpace = (totalBlocks * blockSize) / 1048576f
            val freeSpace = (availableBlocks * blockSize) / 1048576f
            return totalSpace - freeSpace
        }

    fun getCacheSize(context: Context): Long {
        val cacheDirs = arrayOf(
            context.cacheDir,
            context.externalCacheDir,
            File(context.filesDir, "webviewCache"),
            File(context.filesDir, "okhttpCache")
            // Adicione outros diretórios de cache relevantes aqui, se necessário
        )

        var totalCacheSize = 0L

        for (cacheDir in cacheDirs) {
            totalCacheSize += calculateFolderSize(cacheDir!!)
        }

        return totalCacheSize
    }

    private fun calculateFolderSize(directory: File): Long {
        var size = 0L

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    size += if (file.isDirectory) {
                        calculateFolderSize(file)
                    } else {
                        file.length()
                    }
                }
            }
        }

        return size
    }

    fun File.calculateSizeRecursively(): Long {
        return walkBottomUp().fold(0L) { acc, file -> acc + file.length() }
    }

    fun deleteCacheFiles() {
        val file = File("/storage/emulated/0/Android/data/com.google.android.youtube/cache")
        if (file.delete()) {
            Log.i("delete", "File deleted successfully.")
        } else {
            Log.i("delete","Error in deleting file.")
        }
    }




}
