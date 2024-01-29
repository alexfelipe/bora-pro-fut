package br.com.alexf.boraprofut.di

import androidx.room.Room
import br.com.alexf.boraprofut.data.database.BoraProFutDatabase
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.balancedteams.BalancedTeamViewModel
import br.com.alexf.boraprofut.features.drawteams.DrawTeamsViewModel
import br.com.alexf.boraprofut.features.players.PlayersViewModel
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.features.randomteams.RandomTeamsViewModel
import br.com.alexf.boraprofut.features.randomteams.GameViewModel
import br.com.alexf.boraprofut.features.game.usecase.GameUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.single

//TODO vamo trocar ideia de onde deixar esse código

val appModule = module {
    viewModelOf(::PlayersViewModel)
    viewModelOf(::DrawTeamsViewModel)
    viewModelOf(::RandomTeamsViewModel)
    viewModelOf(::BalancedTeamViewModel)
    viewModelOf(::GameViewModel)
    singleOf(::TeamDrawerUseCase)
    singleOf(::GameUseCase)
}

val dataModule = module {
    singleOf(::PlayersRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            BoraProFutDatabase::class.java,
            "bora-pro-fut.db"
        ).build()
    }
    single {
        get<BoraProFutDatabase>().playerDao()
    }
}