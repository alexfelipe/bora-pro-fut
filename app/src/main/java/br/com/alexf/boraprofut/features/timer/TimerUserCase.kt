package br.com.alexf.boraprofut.features.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerUserCase {

    private var job: Job = Job()
    var isPause = true
        private set
    private var currentTime = 0L
    var minutes = 0L
        set(value) {
            field = value * 60000
            currentTime = field
        }
    private var _timer = MutableStateFlow(currentTime)
    var timer = _timer.asStateFlow()

    fun startTimer() {
        job = CoroutineScope(IO).launch {
            isPause = false
            while (currentTime > 0) {
                println("$isPause $currentTime")
                delay(1000)
                if (isPause) {
                    continue
                }
                currentTime -= 1000
                _timer.update {
                    currentTime
                }
            }
        }
    }

    fun resume() {
        isPause = false
    }

    fun pause() {
        isPause = true
    }

}
