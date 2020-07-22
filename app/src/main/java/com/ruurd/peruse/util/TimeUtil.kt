package com.ruurd.peruse.util

import android.content.Context
import com.ruurd.peruse.R
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun toTime(duration: Long) = TimeHelper(duration)

    class TimeHelper(duration: Long) {
        var hours: Long = TimeUnit.MILLISECONDS.toHours(duration)
        var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(duration)
        )
        var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(duration)
        )

        fun format(context: Context, timeFormat: TimeFormat = TimeFormat.hhMMss): String {
            if (hours >= 1) {
                if (timeFormat == TimeFormat.hhMMss)
                    return String.format(
                        context.getString(
                            when (timeFormat) {
                                TimeFormat.hhMMss -> { R.string.long_time_format }
                                TimeFormat.COLON -> { R.string.long_time_format_colon }
                            },
                            hoursFormatted(),
                            minutesFormatted(),
                            secondsFormatted()
                        )
                    )
            }
            return String.format(
                context.getString(
                    when (timeFormat) {
                        TimeFormat.hhMMss -> { R.string.time_format }
                        TimeFormat.COLON -> { R.string.time_format_colon }
                    },
                    minutesFormatted(),
                    secondsFormatted()
                )
            )
        }

        fun hoursFormatted() = hours.toString()
        fun minutesFormatted() = minutes.toString().padStart(2, '0')
        fun secondsFormatted() = seconds.toString().padStart(2, '0')

    }

    enum class TimeFormat {
        hhMMss,
        COLON
    }
}