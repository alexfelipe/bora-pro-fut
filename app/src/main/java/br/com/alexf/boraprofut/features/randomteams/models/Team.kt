package br.com.alexf.boraprofut.features.randomteams.models

import br.com.alexf.boraprofut.features.players.model.Player

data class Team(
    val players: Set<Player>,
) {

    val level get() = players.sumOf { it.level }
}