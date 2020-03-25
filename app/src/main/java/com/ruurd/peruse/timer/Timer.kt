package com.ruurd.peruse.timer

import kotlinx.coroutines.*

class Timer : ITimer {
    var onTickListener: ITimer.OnTickListener? = null
    var onStateChangeListener: ITimer.OnStateChangeListener? = null

    var time: Long = 0L
        get() = currentTime
        private set

    private var currentTime: Long = 0L
    private var state: State = State.IDLE

    private val timerInterval: Long = 100L
    private var timeRoutine: Job? = null

    override fun start() {
        setState(State.RUNNING)
        startTimerRoutine()
    }

    override fun stop() {
        if (timeRoutine != null) {
            timeRoutine!!.cancel()
            setState(State.STOPPED)

            GlobalScope.launch {
                delay(500)
                currentTime = 0L
            }
        }
    }

    override fun pause() {
        if (timeRoutine != null) {
            timeRoutine!!.cancel()
            setState(State.PAUSED)
        }
    }

    private fun startTimerRoutine() {
        timeRoutine = GlobalScope.launch {
            while (state == State.RUNNING) {
                delay(timerInterval)
                currentTime += timerInterval
                onTickListener?.onTick(currentTime)
            }
        }
    }

    private fun setState(state: State) {
        this.state = state
        onStateChangeListener?.onStateChange(state, currentTime)
    }

    fun getState() = this.state
}