package br.com.alexf.boraprofut.features.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class TimerCountDown {

    private var job: Job = Job()
    var isPause by Delegates.observable(true) { _, old, newValue ->
        if (old != newValue && !newValue) {
            startTimer()
        }
    }
    private var remainingTimeMillis = 0L
    var timeMillis = 0L
        set(value) {
            field = value
            remainingTimeMillis = field
        }
    private var _timer = MutableStateFlow(remainingTimeMillis)
    var timer = _timer.asStateFlow()
    var tickDelay: Long = 500L

    fun startTimer() {
        job.cancel()
        job = CoroutineScope(IO).launch {
            isPause = false
            while (remainingTimeMillis > 0) {
                val currentTimeMillis = System.currentTimeMillis()
                delay(tickDelay)
                if (isPause) {
                    break
                }
                remainingTimeMillis -= (System.currentTimeMillis() - currentTimeMillis)
                _timer.update {
                    remainingTimeMillis
                }
            }
            isPause = true
        }
    }

    fun progress(): Float =
        if (remainingTimeMillis > 0) {
            ((timeMillis - remainingTimeMillis) / timeMillis.toFloat())
        } else 0f

}
