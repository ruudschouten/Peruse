package com.ruurd.peruse

import com.ruurd.peruse.timer.ITimer
import com.ruurd.peruse.timer.State
import com.ruurd.peruse.timer.TimePeriodTimer
import org.junit.After
import org.junit.Before
import org.junit.Test

class TimerTest {

    private lateinit var timer: TimePeriodTimer

    private var timerTime: Long = 0L
    private var timerStateTime: Long = 0L
    private var timerState: State = State.IDLE

    @Before
    fun setup() {
        timer = TimePeriodTimer()
        timer.onTickListener = object : ITimer.OnTickListener {
            override fun onTick(currentTime: Long) {
                timerTime = currentTime
                println(timerTime)
            }
        }
        timer.onStateChangeListener = object : ITimer.OnStateChangeListener {
            override fun onStateChange(state: State, currentTime: Long) {
                timerState = state
                timerStateTime = currentTime
                println("State changed to $timerState at $timerStateTime")
            }
        }
    }

    @After
    fun cleanup() {
        timer.stop()
        timer.stopTimerRoutine()
    }

    @Test
    fun testTimer() {
        timer.start()
        Thread.sleep(2500)
        timer.pause()

        Thread.sleep(1500)

        assert(timerTime in 2400L..2600L)

        timer.start()
        Thread.sleep(1500)
        timer.pause()

        assert(timerTime in 3900L..4100L)

        Thread.sleep(500)

        timer.stop()
        assert(timerTime == timerStateTime)
    }
}