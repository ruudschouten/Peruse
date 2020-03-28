package com.ruurd.peruse.util

import android.content.Context
import com.ruurd.peruse.R
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun toTime(duration: Long) = TimeHelper(duration)

    class TimeHelper(duration: Long) {
        var hours: Long = TimeUnit.MILLISECONDS.toHours(duration)
        var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(duration) - removeFromMinutes()
        var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(duration) - removeFromSeconds()

        fun format(context: Context): String {
            if (hours > 1) {
                return String.format(
                    context.getString(
                        R.string.long_time_format,
                        hoursFormatted(),
                        minutesFormatted(),
                        secondsFormatted()
                    )
                )
            }
            return String.format(
                context.getString(
                    R.string.time_format,
                    minutesFormatted(),
                    secondsFormatted()
                )
            )
        }

        fun hoursFormatted() = hours.toString().padStart(2, '0')
        fun minutesFormatted() = minutes.toString().padStart(2, '0')
        fun secondsFormatted() = seconds.toString().padStart(2, '0')

        private fun removeFromMinutes(): Long {
            return (hours * 60)
        }

        private fun removeFromSeconds(): Long {
            return (hours * 360 + minutes * 60)
        }
    }
}