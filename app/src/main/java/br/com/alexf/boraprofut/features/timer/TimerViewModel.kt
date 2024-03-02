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
    val isPause: Boolean = true
)

class TimerViewModel(
    private val timerUseCase: TimerUserCase
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
                                isPause = timerUseCase.isPause
                        )
                    }
                }
        }
    }

    fun setMinutes(minutes: Long) {
        timerUseCase.minutes = minutes
        _uiState.update {
            it.copy(currentTime = timerUseCase.minutes)
        }
        timerUseCase.startTimer()
    }

    fun resume() {
        timerUseCase.resume()
        _uiState.update {
            it.copy(isPause = false)
        }
    }

    fun pause() {
        timerUseCase.pause()
        _uiState.update {
            it.copy(isPause = true)
        }
    }

}