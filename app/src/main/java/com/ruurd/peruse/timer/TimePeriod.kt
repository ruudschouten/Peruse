package com.ruurd.peruse.timer

class TimePeriod(var startTime: Long) {
    var currentTime: Long = 0L
    var endTime: Long = 0L

    fun update(currentTime: Long) {
        this.currentTime = currentTime
    }

    fun finish(currentTime: Long) {
        endTime = currentTime
    }

    fun isFinished(): Boolean = endTime > 0

    fun duration(): Long {
        if (isFinished()) {
            return endTime - startTime
        } else if (currentTime > 0) {
            return currentTime - startTime
        }
        return 0
    }
}
