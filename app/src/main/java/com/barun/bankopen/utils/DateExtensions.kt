package com.barun.bankopen.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_MONTH_DAY_YEAR = "LLL dd, yyyy"
const val STANDARD_DATE_FORMAT = "yyyy-MM-dd"

/***
 * Current Calendar Instance
 */
fun getCalendarInstance(): Calendar = Calendar.getInstance()

/***
 * Current Date at Mid Night
 */
fun getMidNightCalendar(): Calendar {
    return getCalendarInstance().also {
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
    }
}

/***
 * String to Date
 */
fun String.toDate(format: String): Date? {
    return try {
        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        formatter.parse(this)
    } catch (e: java.lang.Exception) {
        logExecption(e)
        null
    }
}

/***
 * String to String Formatted
 * Eg: Input: 2021-06-14 Output: Jun 14, 2021
 */
fun String.toStringFormatted(fromFormat: String, toFormat: String): String? {
    return try {
        val fromFormatter = SimpleDateFormat(fromFormat, Locale.ENGLISH)
        val toFormatter = SimpleDateFormat(toFormat, Locale.ENGLISH)
        toFormatter.format(fromFormatter.parse(this))
    } catch (e: Exception) {
        logExecption(e)
        null
    }
}

/***
 * Date to String Formatted
 */
fun Date.toStringFormatted(format: String): String? {
    return try {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.format(this)
    } catch (e: Exception) {
        logExecption(e)
        null
    }
}

private fun logExecption(e: Exception) {
    Log.e("DateExtensions", e.localizedMessage)
}

/***
 * Long to Date
 */
fun Long.toDate(): Date {
    val calendar = getCalendarInstance().apply {
        timeInMillis = this@toDate
    }
    return calendar.time
}

/***
 * Long to String
 */
fun Long.toFormattedString(format: String): String? {
    return try {
        val calendar = getCalendarInstance().apply {
            timeInMillis = this@toFormattedString
        }
        SimpleDateFormat(format, Locale.ENGLISH).run {
            this.format(calendar.time)
        }
    } catch (e: Exception) {
        logExecption(e)
        null
    }
}

