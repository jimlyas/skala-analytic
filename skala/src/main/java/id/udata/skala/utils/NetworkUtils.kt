@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package id.udata.analytics.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo.State.CONNECTED
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.format.Formatter
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 * @param context context to get network info
 */

class NetworkUtils(private val context: Context) {

    private var usingWifi = false

    /**
     * @return Ip Address of current device
     */
    fun getIPAddress(): String {
        val conMan =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val wifi = conMan.getNetworkInfo(1).state
        return if (wifi == CONNECTED) {
            usingWifi = true
            val wifiManager = context.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        } else {
            val ipAddress: String
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (current in interfaces) {
                    val addresses: List<InetAddress> =
                        Collections.list(current.inetAddresses)
                    for (currentAddress in addresses) {
                        if (!currentAddress.isLoopbackAddress) {
                            ipAddress = currentAddress.hostAddress
                            return ipAddress
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
            "N/A"
        }
    }

    /**
     * @return Carrier of current device
     */
    fun getCarrier(): String {
        val carrierName: String
        return if (usingWifi) {
            carrierName = "Using WI-FI"
            carrierName
        } else {
            val manager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            carrierName = manager.networkOperatorName
            carrierName
        }
    }

    /**
     * @return network type of current device
     */
    fun getNetworkType(): String {
        return if (usingWifi) {
            "WI-FI"
        } else {
            "Mobile Network"
        }
    }
}