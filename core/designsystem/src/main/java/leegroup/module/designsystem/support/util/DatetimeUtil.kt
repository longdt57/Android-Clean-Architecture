package leegroup.module.designsystem.support.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DatetimeUtil {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun formatDateTime(
        timestamp: Long = Date().time,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val format = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        format.timeZone = timeZone
        return format.format(Date(timestamp))
    }
}