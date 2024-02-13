package br.com.alexf.futzinmaroto.features.game.usecase

import br.com.alexf.futzinmaroto.data.repositories.PlayersRepository
import br.com.alexf.futzinmaroto.features.game.model.ReadyMadeGames
import br.com.alexf.futzinmaroto.features.game.model.TeamAtStandby

data class ResultGames(
    val readyMadeGamesList: List<ReadyMadeGames>,
    val teamAtStandbyList: List<TeamAtStandby>
)

class GameUseCase (
    private val repository: PlayersRepository
) {
    fun getGames(): ResultGames {
        val shuffledTeams = repository.games.value.toList().shuffled()
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
