package br.com.alexf.futzinmaroto.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import br.com.alexf.futzinmaroto.data.database.FutzinMarotoDatabase
import br.com.alexf.futzinmaroto.data.repositories.PlayersRepository
import br.com.alexf.futzinmaroto.features.balancedTeams.BalancedTeamViewModel
import br.com.alexf.futzinmaroto.features.drawTeams.DrawTeamsViewModel
import br.com.alexf.futzinmaroto.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.futzinmaroto.features.game.usecase.GameUseCase
import br.com.alexf.futzinmaroto.features.playersForm.PlayersFormViewModel
import br.com.alexf.futzinmaroto.features.game.GameViewModel
import br.com.alexf.futzinmaroto.features.randomteams.RandomTeamsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PlayersFormViewModel)
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
            FutzinMarotoDatabase::class.java,
            "futzin-maroto.db"
        ).build()
    }
    single {
        get<FutzinMarotoDatabase>().playerDao()
    }
    single {
        PreferenceDataStoreFactory.create {
            androidContext()
                .preferencesDataStoreFile("user_preferences")
        }
    }
}