package br.com.alexf.boraprofut.features.game.usecase

import br.com.alexf.boraprofut.features.game.mock.teamList
import br.com.alexf.boraprofut.features.game.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game.model.TeamAtStandby

data class ResultGames(
    val readyMadeGamesList: List<ReadyMadeGames>,
    val teamAtStandbyList: List<TeamAtStandby>
)

class GameUseCase {
    fun getGames(): ResultGames {
        val shuffledTeams = teamList.shuffled()
        val readyMadeGames = mutableListOf<ReadyMadeGames>()
        val teamAtStandby = mutableListOf<TeamAtStandby>()
        for (i in shuffledTeams.indices step 2) {
            val time1 = shuffledTeams[i]
            val time2 = shuffledTeams.getOrNull(i + 1)
           time2?.let{
                readyMadeGames.add(ReadyMadeGames(timeA = time1.name, timeB = time2.name))
            } ?: run {
                teamAtStandby.add(TeamAtStandby(name = time1.name))
            }
        }
        return ResultGames(readyMadeGames, teamAtStandby)
    }
}
