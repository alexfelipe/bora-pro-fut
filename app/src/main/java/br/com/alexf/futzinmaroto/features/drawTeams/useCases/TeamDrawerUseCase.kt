package br.com.alexf.futzinmaroto.features.drawTeams.useCases

import br.com.alexf.futzinmaroto.models.Player
import kotlin.math.ceil

class TeamDrawerUseCase {

    fun drawRandomTeams(
        players: Set<Player>,
        playersPerTeam: Int
    ): List<Set<Player>> {
        return players
            .shuffled()
            .chunked(playersPerTeam) {
                it.toSet()
            }
    }

    fun drawBalancedTeams(
        players: Set<Player>,
        playersPerTeam: Int
    ): List<Set<Player>> {
        val amountTeams = ceil(players.size / playersPerTeam.toFloat()).toInt()
        val sortedPlayers = players.shuffled()
            .sortedBy {
                it.level
            }.toMutableList()

        val teamsWithBestPlayersIncluded = MutableList(amountTeams) {
            val bestPlayer = sortedPlayers.removeLast()
            mutableListOf(bestPlayer)
        }

        while (sortedPlayers.isNotEmpty()) {
            teamsWithBestPlayersIncluded.forEach {
                if (sortedPlayers.isEmpty()) {
                    return@forEach
                }
                it.add(sortedPlayers.removeFirst())
            }
        }

        return teamsWithBestPlayersIncluded.map {
            it.toSet()
        }
    }

}
