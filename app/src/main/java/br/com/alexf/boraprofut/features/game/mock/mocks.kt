package br.com.alexf.boraprofut.features.game.mock

import br.com.alexf.boraprofut.features.game.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game_screen.model.Team

val mockReadyMadeGames = listOf(
    ReadyMadeGames("Flamengo", "Vasco", "Grupo A"),
    ReadyMadeGames("São Paulo", "Flamengo", "Grupo B"),
    ReadyMadeGames("Gremio", "Bahia", "Grupo C"),
    ReadyMadeGames("Santos", "Corinthians", "Grupo D"),
    ReadyMadeGames("Palmeiras", "Atletico", "Grupo E"),
)

val teamList = listOf(
    Team("Flamengo", "Barretos", "Andre"),
    Team("Bahia", "Guarati", "Felipe"),
    Team("Fluminense", "Egito", "Albert"),
    Team("Egito", "Mage", "Farao"),
    Team("Tocantins", "Oque", "Guto"),
)