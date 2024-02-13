package br.com.alexf.futzinmaroto.features.game.model

data class Team(
    val name: String,
    val region: String = "",
    val captainFromTeam: String ="",
)