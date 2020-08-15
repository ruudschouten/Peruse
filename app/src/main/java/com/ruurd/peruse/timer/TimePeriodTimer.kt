package com.ruurd.peruse.timer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimePeriodTimer : ITimer {

    var onTickListener: ITimer.OnTickListener? = null
    var onStateChangeListener: ITimer.OnStateChangeListener? = null

    var timePeriods: MutableList<TimePeriod> = mutableListOf()
        private set
    var currentTimePeriod: TimePeriod? = null
        private set

    var state: State = State.IDLE
        private set

    private var timerRoutine: Job? = null



    override fun start() {
        if (currentTimePeriod == null) {
            currentTimePeriod = TimePeriod(System.currentTimeMillis())
        }

        startTimerRoutine()

        setState(State.RUNNING)
    }

    override fun stop() {
        stopTimerRoutine()
        finishTimePeriod()

        setState(State.STOPPED)
    }

    override fun pause() {
        stopTimerRoutine()
        finishTimePeriod()

        setState(State.PAUSED)
    }

    private fun finishTimePeriod() {
        val stopTime = System.currentTimeMillis()

        if (currentTimePeriod != null) {
            currentTimePeriod!!.finish(stopTime)
            timePeriods.add(currentTimePeriod!!)
            currentTimePeriod = null

            onTickListener?.onTick(getCurrentDuration())
        }
    }

    private fun startTimerRoutine() {
        timerRoutine = GlobalScope.launch {
            while (state != State.STOPPED) {
                delay(UPDATE_INTERVAL)

                if (currentTimePeriod != null) {
                    currentTimePeriod!!.update(System.currentTimeMillis())
                    onTickListener?.onTick(getCurrentDuration())
                }
            }
        }
    }

    fun stopTimerRoutine() {
        timerRoutine?.cancel()
    }

    /**
     * Gets the current duration of this timer.
     *
     * @return The combined duration of all [TimePeriod]s,
     * including the current one, if it exists.
     */
    fun getCurrentDuration(): Long {
        if (currentTimePeriod == null) {
            return duration()
        }
        return duration() + currentTimePeriod!!.duration()
    }

    /**
     * Gets the combined duration of previous [TimePeriod]s.
     *
     * @return the duration of all previous [TimePeriod]s.
     */
    private fun duration(): Long {
        var duration = 0L

        timePeriods.forEach { duration += it.duration() }

        return duration
    }

    private fun setState(state: State) {
        this.state = state
        onStateChangeListener?.onStateChange(this.state, getCurrentDuration())
    }

    companion object {
        private const val UPDATE_INTERVAL = 100L
    }
}