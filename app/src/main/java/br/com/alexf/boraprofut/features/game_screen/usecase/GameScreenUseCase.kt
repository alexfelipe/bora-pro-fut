package br.com.alexf.boraprofut.features.game_screen.usecase

import android.util.Log
import br.com.alexf.boraprofut.features.game_screen.mock.teamList
import br.com.alexf.boraprofut.features.game_screen.model.ReadyMadeGames
import kotlin.text.Typography.times

class GameScreenUseCase {
    fun getGames() : List<ReadyMadeGames> {
        val timesEmbaralhados = teamList.shuffled()
        val listaPartidas = mutableListOf<ReadyMadeGames>()
        if (timesEmbaralhados.size % 2 == 0) {
            for (i in 0 until timesEmbaralhados.size step 2) {
                val time1 = timesEmbaralhados[i]
                val time2 = timesEmbaralhados[i + 1]
                listaPartidas.add(ReadyMadeGames(timeA = time1.name, timeB = time2.name))
            }
        }
        return listaPartidas
    }
}
