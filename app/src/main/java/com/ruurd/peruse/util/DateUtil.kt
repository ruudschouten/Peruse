package com.ruurd.peruse.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun format(duration: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration
        return formatter.format(calendar.time)
    }

    fun formatWithTime(duration: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration
        return formatter.format(calendar.time)
    }
}