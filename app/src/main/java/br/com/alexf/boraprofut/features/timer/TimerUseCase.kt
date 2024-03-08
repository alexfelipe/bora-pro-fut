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

class TimerUseCase {

    private var job: Job = Job()
    var isPause by Delegates.observable(true) { _, old, newValue ->
        if(old != newValue && !newValue){
            startTimer()
        }
    }
    private var currentTimeMillis = 0L
    var timeMillis = 0L
        set(value) {
            field = value
            currentTimeMillis = field
        }
    private var _timer = MutableStateFlow(currentTimeMillis)
    var timer = _timer.asStateFlow()

    fun startTimer() {
        job.cancel()
        job = CoroutineScope(IO).launch {
            isPause = false
            while (currentTimeMillis > 0) {
                delay(1000)
                if (isPause) {
                    break
                }
                currentTimeMillis -= 1000
                _timer.update {
                    currentTimeMillis
                }
            }
        }
    }

}
