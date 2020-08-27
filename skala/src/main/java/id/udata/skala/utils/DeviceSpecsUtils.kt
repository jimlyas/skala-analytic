@file:Suppress("DEPRECATION")

package id.udata.analytics.utils

import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import android.os.Environment
import android.os.StatFs
import java.util.Locale

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 * @param ctx context to get info
 */

class DeviceSpecsUtils(private val ctx: Context) {

    /**
     * @return device name
     */
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.toLowerCase(Locale.ROOT).startsWith(manufacturer.toLowerCase(Locale.ROOT))) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + ";" + model
        }
    }

    /**
     * call this method to capitalize string
     */
    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    /**
     * @return size of empty RAM
     */
    fun getTotalRAM(): String {
        val mi = MemoryInfo()
        val activityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return formatSize(mi.totalMem)
    }

    /**
     * @return size in MB
     */
    private fun formatSize(size: Long): String {
        var resultSize = size
        var suffix: String? = null
        if (resultSize >= 1024) {
            suffix = " KB"
            resultSize /= 1024
            if (resultSize >= 1024) {
                suffix = " MB"
                resultSize /= 1024
            }
        }
        val resultBuffer = StringBuilder(resultSize.toString())
        var commaOffset = resultBuffer.length - 3
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',')
            commaOffset -= 3
        }
        if (suffix != null) resultBuffer.append(suffix)
        return resultBuffer.toString()
    }

    /**
     * @return total empty storage
     */
    fun getStorage(): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val totalBlocks = stat.blockCount.toLong()
        return formatSize(totalBlocks * blockSize)
    }

    val cPU: String = Build.HARDWARE

    val firmware: String = VERSION.RELEASE
}