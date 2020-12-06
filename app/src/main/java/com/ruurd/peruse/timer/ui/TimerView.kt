package com.ruurd.peruse.timer.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.ruurd.peruse.R
import com.ruurd.peruse.databinding.TimerLayoutBinding
import com.ruurd.peruse.timer.ITimer
import com.ruurd.peruse.timer.State
import com.ruurd.peruse.timer.TimePeriodTimer
import com.ruurd.peruse.util.NotificationUtil
import com.ruurd.peruse.util.TimeUtil
import kotlinx.coroutines.*

class TimerView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet),
    ITimer,
    ITimer.OnTickListener,
    ITimer.OnStateChangeListener {

    private var timer = TimePeriodTimer()
    private var job = Job()
    private var scope = CoroutineScope(job + Dispatchers.Main)

    // region Attribute Fields
    var timeSize: Float = 0f
        set(value) {
            field = value
            setTimeTextSize(field)
            invalidate()
        }
    var labelSize: Float = 0f
        set(value) {
            field = value
            setTimeLabelSize(field)
            invalidate()
        }
    // endregion

    // region View stuff

    private var binding: TimerLayoutBinding =
        TimerLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.TimerView, 0, 0).apply {
            try {
                timeSize = getDimension(
                    R.styleable.TimerView_time_text_size,
                    context.resources.getDimension(R.dimen.text_size_extremely_large)
                )
                labelSize = getDimension(
                    R.styleable.TimerView_label_text_size,
                    context.resources.getDimension(R.dimen.text_size_medium)
                )
            } finally {
                recycle()
            }
        }

        setupTimer()
    }

    private fun setTimeTextSize(size: Float) {
        binding.timeSeconds.textSize = size
        binding.timeMinutes.textSize = size
        binding.timeHours.textSize = size
    }

    private fun setTimeLabelSize(size: Float) {
        binding.timeSecondsLabel.textSize = size
        binding.timeMinutesLabel.textSize = size
        binding.timeHoursLabel.textSize = size
    }

    private fun setTimeText(currentTime: Long) {
        val time = TimeUtil.toTime(currentTime)

        binding.timeSeconds.text = time.secondsFormatted()
        binding.timeMinutes.text = time.minutesFormatted()
        binding.timeHours.text = time.hoursFormatted()
    }

    private fun setStatusText(state: State) {
        binding.timerStatus.text = when (state) {
            State.IDLE -> context.getString(R.string.timer_status_idle)
            State.RUNNING -> context.getString(R.string.timer_status_running)
            State.PAUSED -> context.getString(R.string.timer_status_paused)
            else -> return
        }
    }

    fun onDestroy() {
        job.cancel()
    }

    // endregion

    // region Timer
    private fun setupTimer() {
        timer.onTickListener = this
        timer.onStateChangeListener = this
    }

    override fun start() {
        timer.start()
    }

    override fun stop() {
        timer.stop()
    }

    override fun pause() {
        timer.pause()
    }

    override fun onTick(currentTime: Long) {
        scope.launch {
            setTimeText(currentTime)
            NotificationUtil.updateTime(getFormattedTime(TimeUtil.TimeFormat.COLON))
        }
    }

    override fun onStateChange(state: State, currentTime: Long) {
        scope.launch {
            setTimeText(currentTime)
            setStatusText(state)
        }
    }

    fun getState() = timer.state

    fun getTime() = timer.getCurrentDuration()

    fun getFormattedTime(format: TimeUtil.TimeFormat = TimeUtil.TimeFormat.hhMMss) =
        TimeUtil.toTime(getTime()).format(context, format)

    fun getTimer() = timer

    // endregion
}