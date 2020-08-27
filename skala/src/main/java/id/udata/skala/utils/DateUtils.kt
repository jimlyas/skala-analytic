package id.udata.analytics.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 */

/**
 * @return String of current date with format dd-MMM-yyyy HH:mm:ss:SSS
 */
fun getCurrentDate(): String {
    val currentTime = Date()
    val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS", Locale.getDefault())
    return df.format(currentTime)
}

/**
 * @return Date? from string with format dd-MMM-yyyy HH:mm:ss:SSS using default Locale
 */
fun String.formatToDate(): Date? {
    val format = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS", Locale.getDefault())
    return format.parse(this)
}

/**
 * @return String? from date with format dd-MMM-yyyy HH:mm:ss:SSS using default Locale
 */
fun Date.formatToString(): String? {
    val format = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS", Locale.getDefault())
    return format.format(this)
}