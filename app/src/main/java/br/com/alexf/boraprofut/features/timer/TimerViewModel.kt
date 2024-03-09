package br.com.alexf.boraprofut.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TimerUiState(
    val currentTime: Long = 0L,
    val isPause: Boolean = true,
    val times: List<Long> = listOf(7L, 10L, 15L),
    val timerProgress: Float = 0f
)

class TimerViewModel(
    private val timerUseCase: TimerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState
        .asStateFlow()

    init {
        viewModelScope.launch {
            timerUseCase.timer
                .collectLatest { currentTime ->
                    _uiState.update {
                        it.copy(
                            currentTime = currentTime,
                            isPause = timerUseCase.isPause,
                            timerProgress = timerUseCase.progress()
                        )
                    }
                }
        }
    }

    fun setMinutes(minutes: Long) {
        timerUseCase.timeMillis = minutes * 60000
        _uiState.update {
            it.copy(currentTime = timerUseCase.timeMillis)
        }
        timerUseCase.startTimer()
    }

    fun resume() {
        timerUseCase.isPause = false
        _uiState.update {
            it.copy(isPause = false)
        }
    }

    fun pause() {
        timerUseCase.isPause = true
        _uiState.update {
            it.copy(isPause = true)
        }
    }

}