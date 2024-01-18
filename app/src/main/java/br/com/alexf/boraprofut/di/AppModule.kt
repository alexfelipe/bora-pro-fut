package br.com.alexf.boraprofut.di

import br.com.alexf.boraprofut.features.players.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

//TODO vamo trocar ideia de onde deixar esse código

val appModule = module {
    viewModelOf(::PlayersViewModel)
}