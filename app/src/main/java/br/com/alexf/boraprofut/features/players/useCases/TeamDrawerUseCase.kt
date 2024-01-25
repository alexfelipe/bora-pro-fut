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

        val teams = List(amountTeams) {
            mutableListOf<Player>()
        }

        teams.forEach { team ->
            while (sortedPlayers.isNotEmpty()) {
                if(team.size == playersPerTeam) {
                    return@forEach
                }
                if (team.size.mod(2) == 0) {
                    team.add(sortedPlayers.removeFirst())
                } else {
                    team.add(sortedPlayers.removeLast())
                }
            }
        }
        return teams.map {
            it.toSet()
        }
    }

}
