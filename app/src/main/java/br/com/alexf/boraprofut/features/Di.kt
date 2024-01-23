package br.com.alexf.boraprofut.features

import br.com.alexf.boraprofut.features.game.GameViewModel
import br.com.alexf.boraprofut.features.game.usecase.GameUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val gameScreenModule = module {
    factoryOf(::GameUseCase)
    viewModelOf(::GameViewModel)
}

