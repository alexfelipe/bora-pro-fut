package br.com.alexf.boraprofut.features.players.useCases

import br.com.alexf.boraprofut.models.Player
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

        val bestPlayers = sortedPlayers.takeLast(amountTeams)

        val teams = bestPlayers.map {
            mutableListOf(it)
        }

        while (sortedPlayers.isNotEmpty()) {
            teams.forEach {
                if (sortedPlayers.isEmpty()) {
                    return@forEach
                }
                it.add(sortedPlayers.removeFirst())
            }
        }

        return teams.map {
            it.toSet()
        }
    }

}
