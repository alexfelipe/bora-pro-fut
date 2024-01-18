package br.com.alexf.boraprofut.features.game_screen

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.features.game_screen.usecase.GameScreenUseCase

class GameScreenViewModel(
    val useCase: GameScreenUseCase
) : ViewModel() {

}
