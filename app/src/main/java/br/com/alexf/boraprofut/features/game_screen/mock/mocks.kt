package br.com.alexf.boraprofut.features.game_screen.mock

import br.com.alexf.boraprofut.features.game_screen.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game_screen.model.Team

val mockReadyMadeGames = listOf(
    ReadyMadeGames("Flamengo", "Vasco", "Grupo D - Dia da partida 5 X 3"),
    ReadyMadeGames("São Paulo", "Flamengo", "Grupo C - Dia da partida 1 X 5"),
    ReadyMadeGames("Gremio", "Bahia", "Grupo C - Dia da partida 1 X 5"),
    ReadyMadeGames("Santos", "Corinthians", "Grupo C - Dia da partida 1 X 5"),
    ReadyMadeGames("Palmeiras", "Atletico", "Grupo C - Dia da partida 1 X 5"),
)

val teamList = listOf(
    Team("Flamengo", "Barretos", "Andre"),
    Team("Bahia", "Guarati", "Felipe"),
    Team("Fluminense", "Egito", "Albert"),
    Team("Egito", "Mage", "Farao"),
    Team("Tocantins", "Oque", "Guto"),
    Team("ABC", "Tonem", "Angelica"),
)