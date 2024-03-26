package br.com.alexf.boraprofut.features.drawTeams.useCases

import br.com.alexf.boraprofut.models.Player

class TeamDrawerUseCase {

    fun drawRandomTeams(
        players: Set<Player>,
        playersPerTeam: Int
    ): List<Set<Player>> {
        return getTeams(players = players, playersPerTeam = playersPerTeam)
    }

    fun drawBalancedTeams(
        players: Set<Player>,
        playersPerTeam: Int
    ): List<Set<Player>> {
        return getTeams(isBalancedTeam = true, players = players, playersPerTeam = playersPerTeam)
    }

    private fun getTeams(
        isBalancedTeam: Boolean = false,
        players: Set<Player>,
        playersPerTeam: Int
    ): List<Set<Player>> {

        val onlyGoalKeepers = players.filter { it.isGoalKeeper }.shuffled().toMutableList()
        val onlyPlayers = if (isBalancedTeam) {
            players.filter { !it.isGoalKeeper }.sortedByDescending { it.level }.toMutableList()
        } else {
            players.filter { !it.isGoalKeeper }.shuffled().toMutableList()
        }
        val totalOfGoalKeepers = onlyGoalKeepers.size
        val totalOfTeams: Int = onlyPlayers.size / playersPerTeam
        val teams: MutableList<MutableList<Player>> =
            MutableList(totalOfTeams) { mutableListOf() }
        var firstTeamWithoutGoalKeeper = 0
        // add players to the teams
        for (current in 0 until playersPerTeam) {
            teams.forEach { playersList ->
                if (onlyPlayers.isNotEmpty() && playersList.size <= playersPerTeam) {
                    playersList.add(onlyPlayers.removeFirst())
                }
            }
        }
        // add goalkeepers to the teams
        teams.forEachIndexed { index, playersList ->
            if (onlyGoalKeepers.isNotEmpty()) {
                playersList.add(0, onlyGoalKeepers.removeFirst())
                firstTeamWithoutGoalKeeper = index + 1
            }
        }
        // when the player list has only one goalkeeper, add the remaining players to the teams without goalkeepers
        if (totalOfGoalKeepers == 1) {
            teams.subList(firstTeamWithoutGoalKeeper, teams.size).forEach {
                if (onlyPlayers.isNotEmpty())
                    it.add(onlyPlayers.removeFirst())
            }
        }
        // when players remain, add to a new team
        if (onlyPlayers.isNotEmpty()) {
            teams.add(onlyPlayers)
        }

        return teams.map { it.toSet() }
    }

}
