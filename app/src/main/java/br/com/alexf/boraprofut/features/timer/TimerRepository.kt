package br.com.alexf.boraprofut.features.timer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class TimerAction {
    data object PAUSE : TimerAction()
    data object RESTART : TimerAction()
    data object NEW_TIME : TimerAction()
    data object CONTINUE : TimerAction()
}

data class TimerLog(
    val timerAction: TimerAction,
    val time: Long,
    val timeStamp: Long = System.currentTimeMillis()
)

class TimerRepository {

    val timerLog = _timerLog.asStateFlow()

    fun saveLog(currentTime: Long, action: TimerAction) {
        _timerLog.update {
            listOf(
                TimerLog(
                    action,
                    currentTime,
                    System.currentTimeMillis()
                )
            ) + it
        }
    }

    companion object {
        private val _timerLog =
            MutableStateFlow<List<TimerLog>>(emptyList())
    }

}
