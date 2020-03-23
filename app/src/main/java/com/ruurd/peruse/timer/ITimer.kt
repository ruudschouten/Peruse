package com.ruurd.peruse.timer

interface ITimer {
    fun start()
    fun stop()
    fun pause()

    interface OnTickListener {
        fun onTick(currentTime: Long)
    }

    interface OnStateChangeListener {
        fun onStateChange(state: State, currentTime: Long)
    }
}