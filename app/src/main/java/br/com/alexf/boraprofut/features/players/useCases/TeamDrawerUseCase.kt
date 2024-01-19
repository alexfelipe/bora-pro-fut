package br.com.alexf.boraprofut.features.players.useCases

import br.com.alexf.boraprofut.features.players.model.Player

class TeamDrawerUseCase {

    fun drawTeams(
        players: Set<Player>,
        playersPerTeam: Int
    ): List<List<Player>> {
        return players
            .shuffled()
            .chunked(playersPerTeam)
    }

}