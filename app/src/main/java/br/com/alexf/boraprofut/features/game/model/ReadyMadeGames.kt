package br.com.alexf.boraprofut.features.game.model

data class ReadyMadeGames(
    val timeA: String,
    val timeB: String,
    val category: String = "",
    val image: String = "https://conteudo.imguol.com.br/c/esporte/94/2022/07/28/simbolo-do-clube-laguna-o-primeiro-time-vegano-do-brasil-1659039926161_v2_450x450.jpg",
    val teamAtStandby: List<TeamAtStandby> = listOf()
)