package com.ruurd.peruse.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.ruurd.peruse.R
import com.ruurd.peruse.timer.ui.TimerView

object NotificationUtil {
    private const val channelId: String = "PERUSE_TIMER_CHANNEL"
    private const val notificationId: Int = 1255

    private lateinit var context: Context
    private lateinit var timerView: TimerView

    private var builder: Notification.Builder? = null

    private fun getManager(): NotificationManager {
        if (notificationManager == null) {
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager!!
    }

    private var notificationManager: NotificationManager? = null

    fun make(context: Context, timerView: TimerView) {
        // Make sure a channel exists.
        createChannel(context)

        this.context = context
        this.timerView = timerView

        builder = Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_calculate_24dp)
            .setContentText(timerView.getFormattedTime(TimeUtil.TimeFormat.COLON))
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setAutoCancel(false)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder!!.build())
        }
    }

    /*
     * Creates a NotificationChannel which can be used to display notifications.
     * Can be called multiple times, if the channel already exists no operations will occur.
     */
    fun createChannel(context: Context) {
        this.context = context
        val name = "Peruse Timer"
        val descriptionText = "Displays the timer in a notification."
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =
            NotificationChannel(channelId, name, importance).apply { description = descriptionText }
        getManager().createNotificationChannel(channel)
    }

    fun remove() {
        getManager().cancel(notificationId)
        builder = null
    }

    fun updateTime(formattedTime: String) {
        if (builder == null) {
            return
        }
        builder!!.setContentText(formattedTime)
        getManager().notify(notificationId, builder!!.build())
    }
}