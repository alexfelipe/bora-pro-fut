package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.timer.TimerUseCase
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class TimerUseCaseTest {

    @Test
    fun shouldCountDownTimerGivenATimeInMillis() = runTest {
        val times = 5
        val timerUseCase = TimerUseCase()
        timerUseCase.timeMillis = times * 1000L
        timerUseCase.startTimer()
        val firstTimeAfterStart = timerUseCase.timer
            .drop(1)
            .first()
        firstTimeAfterStart shouldBeEqualTo 4000L
        val timesSeries = timerUseCase.timer.take(times)
            .toList()
        timesSeries shouldBeEqualTo listOf(
            4000L,
            3000L,
            2000L,
            1000L,
            0L
        )
    }

}