package br.com.alexf.boraprofut.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TimerUiState(
    val currentTime: Long = 0L,
    val isPause: Boolean = true,
    val times: List<Long> = listOf(5L, 7L, 10L, 15L, 20L),
    val timerProgress: Float = 0f,
    val timerLog: List<TimerLog> = emptyList(),
    val isDisplayTimerLog: Boolean = true,
    val onToggleTimerLog: () -> Unit = {}
)

class TimerViewModel(
    private val timerCountDown: TimerCountDown,
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState
        .combine(timerRepository.timerLog) { uiState, timerLog ->
            uiState.copy(timerLog = timerLog)
        }

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onToggleTimerLog = {
                    _uiState.update {
                        it.copy(isDisplayTimerLog = !it.isDisplayTimerLog)
                    }
                })
        }
        viewModelScope.launch {
            timerCountDown.timer
                .collectLatest { currentTime ->
                    _uiState.update {
                        it.copy(
                            currentTime = currentTime,
                            isPause = timerCountDown.isPause,
                            timerProgress = timerCountDown.progress()
                        )
                    }
                }
        }

    }

    fun setMinutes(minutes: Long) {
        timerCountDown.timeMillis = minutes * 60000
        timerCountDown.isPause = false
        timerCountDown.startTimer()
        _uiState.update {
            timerRepository.saveLog(
                currentTime = it.currentTime,
                action = TimerAction.NEW_TIME
            )
            it.copy(
                isPause = timerCountDown.isPause
            )
        }
    }

    fun resume() {
        timerCountDown.isPause = false
        _uiState.update {
            timerRepository.saveLog(
                currentTime = it.currentTime,
                action = TimerAction.CONTINUE
            )
            it.copy(isPause = false)
        }
    }

    fun pause() {
        timerCountDown.isPause = true
        _uiState.update {
            timerRepository.saveLog(
                currentTime = it.currentTime,
                action = TimerAction.PAUSE
            )
            it.copy(
                isPause = true,
            )
        }
    }

    fun restart() {
        _uiState.update {
            timerRepository.saveLog(
                currentTime = it.currentTime,
                action = TimerAction.RESTART
            )
            it.copy(
                currentTime = 0L, timerProgress = 0f
            )
        }
    }

}