package br.com.alexf.boraprofut.models

data class Team(
    val players: Set<Player>,
) {

    val level
        get() =
            players
                .sumOf { it.level }
                .div(players.size)
}