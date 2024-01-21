package br.com.alexf.boraprofut.features.players.useCases

import br.com.alexf.boraprofut.features.players.model.Player

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

}