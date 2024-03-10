package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.timer.TimerCountDown
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInRange
import org.amshove.kluent.shouldHaveSize
import org.junit.Test

class TimerCountDownTest {

    @Test
    fun shouldCountDownTimerGivenATimeInMillis() = runTest {
        val times = 10
        val timeMillis = times * 1000L
        val timerCountDown = TimerCountDown()
        timerCountDown.tickDelay = 500L
        timerCountDown.timeMillis = timeMillis
        timerCountDown.startTimer()
        val firstTimeAfterStart = timerCountDown.timer
            .drop(1)
            .first()
        firstTimeAfterStart shouldBeInRange ((timeMillis - 1000L)..timeMillis)
        val timesSeries = timerCountDown.timer
            .takeWhile { it > 0L }.toList()
        timesSeries shouldHaveSize (times * 2) - 1
    }

}