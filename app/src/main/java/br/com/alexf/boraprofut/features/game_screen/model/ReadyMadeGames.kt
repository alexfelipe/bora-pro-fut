package br.com.alexf.boraprofut.features.game_screen.model

data class ReadyMadeGames(
    val timeA: String,
    val timeB: String,
    val category: String = "Grupo D - Dia da partida 5 X 3",
)