package br.com.alexf.boraprofut.di

import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.drawteams.DrawTeamsViewModel
import br.com.alexf.boraprofut.features.players.PlayersViewModel
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//TODO vamo trocar ideia de onde deixar esse código

val appModule = module {
    viewModelOf(::PlayersViewModel)
    viewModelOf(::DrawTeamsViewModel)
    singleOf(::TeamDrawerUseCase)
}

val dataModule = module {
    singleOf(::PlayersRepository)
}