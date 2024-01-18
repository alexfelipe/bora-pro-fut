package br.com.alexf.boraprofut.features

import br.com.alexf.boraprofut.features.game_screen.GameScreenViewModel
import br.com.alexf.boraprofut.features.game_screen.usecase.GameScreenUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val gameScreenModule = module {
    factoryOf(::GameScreenUseCase)
    viewModelOf(::GameScreenViewModel)
}

